import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    private static final ProcessBuilder BUILDER = new ProcessBuilder();

    /**
     * javac xx
     * sudo java Main openvpn /etc/openvpn/presk.conf
     */
    public static void main(String... args) {
        System.out.println(Arrays.toString(args));
        System.out.println(exec(args));
    }

    public static String exec(String... script) {
        StringBuilder sb = new StringBuilder();
        try {
            BUILDER.command(script);
            Process process = BUILDER.start();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while (null != (line = br.readLine())) {
                    sb.append(line).append("\r\n");
                }
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while (null != (line = br.readLine())) {
                    sb.append(line).append("\n\n");
                }
            }
            int status = process.waitFor();
            sb.append(String.format("%s exit %s", Arrays.toString(script), status));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
