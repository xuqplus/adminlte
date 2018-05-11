package cn.xuqplus.adminlte.util;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestUtil {

    public static String md5(Object o) throws NoSuchAlgorithmException {
        return DatatypeConverter.printHexBinary(MessageDigest.getInstance("MD5").digest(String.valueOf(o).getBytes()));
    }
}
