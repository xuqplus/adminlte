package cn.xuqplus.adminlte.controller;

import cn.xuqplus.adminlte.client.WeatherClient;
import com.alibaba.fastjson.JSON;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("weather")
public class WeatherController {
    @GetMapping("city")
    public Map getCity(String city) throws DocumentException, NoSuchAlgorithmException, IOException {
        Map map = WeatherClient.getSubCityList(city);
        if (map.get(city) instanceof List) {
            List<Map> content = (List) map.get(city);
            content.forEach(subCity -> {
                subCity.remove("tem1");
                subCity.remove("tem2");
                subCity.remove("windPower");
                subCity.remove("city");
                subCity.remove("stateDetailed");
                subCity.remove("windDir");
                subCity.remove("state1");
                subCity.remove("state2");
                subCity.remove("windState");
                subCity.remove("temNow");
                subCity.remove("humidity");
                subCity.remove("fontColor");
                subCity.remove("cityX");
                subCity.remove("cityY");
                subCity.remove("cityY");
                subCity.remove("time");
                subCity.put("cityCode", subCity.get("url"));
                subCity.remove("url");
            });
        }
        return map;
    }

    @GetMapping("weather")
    public Map getCityWeather(String cityCode) throws DocumentException, NoSuchAlgorithmException, IOException {
        Map map = (Map) JSON.parse(WeatherClient.getCityWeather(cityCode));
        return map;
    }
}
