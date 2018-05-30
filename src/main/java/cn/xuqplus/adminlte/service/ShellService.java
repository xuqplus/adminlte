package cn.xuqplus.adminlte.service;

import cn.xuqplus.adminlte.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;

@Service
public class ShellService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShellService.class);

    private static final ProcessBuilder BUILDER = new ProcessBuilder();
    private static final String PREFIX = "target/classes/";
    private static final String RESOURCE_PREFIX = "sh/";

    public synchronized String exec(String... script) {
        StringBuilder sb = new StringBuilder();
        try {
            BUILDER.command(script);
            Process process = BUILDER.start();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while (null != (line = br.readLine())) {
                    sb.append(line).append("\n");
                }
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while (null != (line = br.readLine())) {
                    sb.append(line).append("\n");
                }
            }
            int status = process.waitFor();
            sb.append(String.format("%s exit %s", Arrays.toString(script), status));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info(sb.toString());
        return sb.toString();
    }

    public String execScript(String... script) throws IOException {
        File file = new File(PREFIX + RESOURCE_PREFIX + script[0]);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            FileUtils.copyInputStreamToFile(this.getClass().getClassLoader().getResourceAsStream(RESOURCE_PREFIX + script[0]), file);
            file.setExecutable(true);
        }
        /*//array copy, for tty running
        String[] r = new String[script.length + 2];
        System.arraycopy(script, 0, r, 2, script.length);
        r[0] = "bash";
        r[1] = "-c";
        r[2] = file.getAbsolutePath();
        return exec(r);*/
        script[0] = file.getAbsolutePath();
        return this.exec("bash", "-c", String.format(StringUtil.nAJoinB(script.length - 1, " ", "%s"), script));
    }
}
