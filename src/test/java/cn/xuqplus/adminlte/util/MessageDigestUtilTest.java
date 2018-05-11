package cn.xuqplus.adminlte.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class MessageDigestUtilTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageDigestUtilTest.class);

    @Test
    public void md5() throws NoSuchAlgorithmException {
        LOGGER.info(MessageDigestUtil.md5("123123"));
        LOGGER.info(MessageDigestUtil.md5("123123@@"));
    }
}