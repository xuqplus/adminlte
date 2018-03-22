package cn.xuqplus.adminlte.controller;

import cn.xuqplus.adminlte.domain.User;
import cn.xuqplus.adminlte.util.PathUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("file")
public class FileController {
    /**
     * 文件下载会占用大量内存
     */
    @GetMapping("users")
    public ResponseEntity users() throws IOException {
        final String csvHeader = "id,name(姓名),password(密码)\n";
        File file = new File("users.csv");
        FileUtils.write(file, csvHeader, "utf8", false);
        List<User> users = DataController.getUsers();
        List<String> csvContent = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat("00000000");
        users.forEach(o -> csvContent.add(String.format("%s,%s,%s", decimalFormat.format(o.getId()), o.getName(), o.getPassword())));
        FileUtils.writeLines(file, "utf8", csvContent, "\n", true);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", file.getName());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    /**
     * flush()流立即写到客户端,如果客户端关闭连接,服务端也相应关闭
     */
    @GetMapping("outputStream")
    public void outputStream(HttpServletResponse response) throws IOException, InterruptedException {
        final String csvHeader = "id,name(姓名),password(密码)\n";
        response.setHeader("attachment", "users.csv");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
        List<User> users = DataController.getUsers();
        DecimalFormat decimalFormat = new DecimalFormat("00000000");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(csvHeader.getBytes());
        for (User o : users) {
            outputStream.write((String.format("%s,%s,%s\n", decimalFormat.format(o.getId()), o.getName(), o.getPassword()).getBytes()));
            outputStream.flush();
            Thread.sleep(100);
        }
        outputStream.close();
    }

    /**
     * public资源放到web目录下,由web容器提供下载
     * 部分公开的资源使用url拦截判断权限
     */
    @GetMapping("mp3")
    public void mp3(HttpServletResponse response, HttpServletRequest request) throws IOException, InterruptedException {
        response.setHeader("attachment", "test.mp3");
        response.setContentType(MediaType.ALL.toString());
        OutputStream outputStream = response.getOutputStream();
        InputStream is = new FileInputStream(new File(PathUtil.path + "classes/static/assets/test.mp3"));
        byte[] bytes = new byte[4096];
        while (is.read(bytes) > 0) {
            outputStream.write(bytes);
            outputStream.flush();
            Thread.sleep(10);
        }
        outputStream.close();
    }

    @GetMapping("break")
    public void break0() {

    }
}
