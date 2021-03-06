package cn.xuqplus.adminlte.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherClient {
    /**
     * 获取城市天气信息
     */
    public static String getCityWeather(String cityCode) throws IOException, NoSuchAlgorithmException {
        HttpClient httpClient = new DefaultHttpClient() {{
            this.getConnectionManager().getSchemeRegistry().register(
                    new Scheme("https", new SSLSocketFactory(SSLContext.getDefault()) {{
                        setHostnameVerifier(ALLOW_ALL_HOSTNAME_VERIFIER);
                    }}, 443));
        }};
        HttpGet httpGet = new HttpGet("http://www.weather.com.cn/data/sk/" + cityCode + ".html");
        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        return new String(EntityUtils.toString(httpEntity).getBytes("iso-8859-1"), "utf8");
    }

    /**
     * 获取城市code
     */
    public static Map getSubCityList(String parent) throws IOException, NoSuchAlgorithmException, DocumentException {
        HttpClient httpClient = new DefaultHttpClient() {{
            this.getConnectionManager().getSchemeRegistry().register(
                    new Scheme("https", new SSLSocketFactory(SSLContext.getDefault()) {{
                        setHostnameVerifier(ALLOW_ALL_HOSTNAME_VERIFIER);
                    }}, 443));
        }};
        HttpGet httpGet = new HttpGet("http://flash.weather.com.cn/wmaps/xml/" + parent + ".xml");
        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        String xml = new String(EntityUtils.toString(httpEntity).getBytes("iso-8859-1"), "utf8");
        return fromElement(DocumentHelper.parseText(xml).getRootElement());
    }

    private static Map fromElement(Element root) {
        List<Attribute> attributes = root.attributes();
        List<Element> elements = root.elements();
        Map map = new HashMap();
        for (Attribute attribute : attributes) {
            map.put(attribute.getQualifiedName(), attribute.getValue());
        }
        List list = new ArrayList();
        for (Element element : elements) {
            list.add(fromElement(element));
        }
        map.put(root.getQualifiedName(), list);
        return map;
    }
}
