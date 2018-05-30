package cn.xuqplus.adminlte.util;

public class ThreadUtil {

    public static final String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
}
