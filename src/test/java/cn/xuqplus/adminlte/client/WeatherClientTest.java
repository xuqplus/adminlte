package cn.xuqplus.adminlte.client;

import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class WeatherClientTest {

    @Test
    public void aa() throws IOException, NoSuchAlgorithmException {
        String aa = WeatherClient.getCityWeather("101190408");
    }

    @Test
    public void bb() throws Exception {
        Map map = WeatherClient.getSubCityList("jiangsu");
    }

    @Test
    public void cc() throws Exception {
    }
}