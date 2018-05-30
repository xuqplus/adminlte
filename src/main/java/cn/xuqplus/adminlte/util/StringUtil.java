package cn.xuqplus.adminlte.util;

public class StringUtil {

    public static final String times(String str, int times) {
        StringBuilder sb = new StringBuilder();
        while (times-- > 0) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static final String nAJoinB(int n, String a, String b) {
        StringBuilder sb = new StringBuilder(b);
        while (n-- > 0) {
            sb.append(a + b);
        }
        return sb.toString();
    }
}
