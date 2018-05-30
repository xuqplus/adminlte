package cn.xuqplus.adminlte.util;

import org.junit.Test;
import org.springframework.util.StringUtils;

public class StringUtilTest {
    @Test
    public void a() {
        String aa = StringUtils.arrayToDelimitedString(new String[5], "%s");
        String[] bb = StringUtils.addStringToArray(new String[5], "%s");
        String cc = StringUtils.quote("%s");
    }

    @Test
    public void b() {
        String aa = StringUtil.times("%s", 5);
        String bb = StringUtil.nAJoinB(5, "%s", " ");
        String cc = StringUtil.nAJoinB(5, " ", "%s");
        String m = Thread.currentThread().getStackTrace()[1].getMethodName();
    }

    @Test
    public void c() {
        String m = ThreadUtil.getCurrentMethodName();
    }
}
