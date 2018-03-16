package cn.xuqplus.adminlte.client;

import com.alibaba.fastjson.JSONObject;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherClientTest {

    @Test
    public void aa() throws IOException, NoSuchAlgorithmException {
        String aa = WeatherClient.getCityWeather("101190408");
    }

    @Test
    public void bb() throws Exception {
        String aa = WeatherClient.getCityCode();
        Document document = DocumentHelper.parseText(aa);
        Element root = document.getRootElement();
        Map map = fromElement(root);
    }
}