package cn.xuqplus.adminlte.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RandomUtilTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomUtilTest.class);

    @Test
    public void get() {
        LOGGER.info(("" + RandomUtil.getString(10)));
    }

    @Test
    public void get1() {
        Map map = new HashMap();
        map.put(1, 123);
        map.put("aa", "bcdef");
        map.put(new ArrayList<>(), "bcdef");
        LOGGER.info((String.valueOf(map)));
    }
}