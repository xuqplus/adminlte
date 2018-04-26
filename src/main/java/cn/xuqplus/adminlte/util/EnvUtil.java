package cn.xuqplus.adminlte.util;

import org.springframework.util.StringUtils;

public class EnvUtil {
    public static final String tryGetEnv(String env, String constant) {
        if (StringUtils.isEmpty(System.getenv(env))) {
            return constant;
        }
        return System.getenv(env);
    }
}
