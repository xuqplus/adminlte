package cn.xuqplus.adminlte.service;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ShellService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShellService.class);

    private static final ProcessBuilder BUILDER = new ProcessBuilder();
    private static final String PREFIX = "target/classes/";
    private static final String RESOURCE_PREFIX = "sh/";

    public synchronized void exec(String script) {
        try {
            BUILDER.command("bash", extractScript(script));
            Process process = BUILDER.start();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while (null != (line = br.readLine())) {
                    LOGGER.error(line);
                }
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while (null != (line = br.readLine())) {
                    LOGGER.info(line);
                }
            }
            int status = process.waitFor();
            LOGGER.info(String.format("%s exit %s", script, status));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String extractScript(String script) throws IOException {
        File file = new File(PREFIX + RESOURCE_PREFIX + script);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            FileUtils.copyInputStreamToFile(this.getClass().getClassLoader().getResourceAsStream(RESOURCE_PREFIX + script), file);
        }
        return file.getAbsolutePath();
    }
}
