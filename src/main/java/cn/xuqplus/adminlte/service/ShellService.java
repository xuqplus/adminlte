package cn.xuqplus.adminlte.service;

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
            BUILDER.command(extractScript(script));
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

    private String[] extractScript(String... script) throws IOException {
        File file = new File(PREFIX + RESOURCE_PREFIX + script[0]);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            FileUtils.copyInputStreamToFile(this.getClass().getClassLoader().getResourceAsStream(RESOURCE_PREFIX + script[0]), file);
        }
        /*数组copy
        String[] r = new String[script.length + 1];
        System.arraycopy(script, 0, r, 1, script.length);
        r[0] = "bash";
        r[1] = file.getAbsolutePath();
        return r;*/
        script[0] = file.getAbsolutePath();
        return script;
    }
}
