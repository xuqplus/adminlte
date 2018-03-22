package cn.xuqplus.adminlte.util;

public class PathUtil {
    public static final String path = PathUtil.class.getResource("").toString().split("classes/")[0].replace("file:", "");
}
