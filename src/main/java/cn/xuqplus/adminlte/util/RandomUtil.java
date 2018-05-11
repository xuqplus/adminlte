package cn.xuqplus.adminlte.util;

import java.util.Random;

public class RandomUtil {
    final static char[] chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    final static Random random = new Random();

    public static char get() {
        return chars[random.nextInt(chars.length)];
    }

    public static char[] get(int n) {
        char[] r = new char[n];
        for (int i = 0; i < n; i++) {
            r[i] = get();
        }
        return r;
    }

    public static String getString(int n) {
        return String.valueOf(get(n));
    }

    public static String a(int n) {
        return String.valueOf(get(n));
    }
}
