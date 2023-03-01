package ${groupPath}.common.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * TODO
 *
 * @author 叶少康
 * @date 2021/9/22 17:36
 */
@Slf4j
public class HttpsUtils extends HttpUtil {

    public static JSONObject httpsPost(String url, Map<String, Object> form, String contentType) {
        HttpRequest httpRequest = HttpUtil.createPost(url);
        httpRequest.header("Content-Type", contentType);
        httpRequest.form(form);
        HttpResponse response = httpRequest.execute();
        return JSONObject.parseObject(response.body());
    }

    public static HttpRequest httpsPost(String url, String contentType) {
        HttpRequest httpRequest = HttpUtil.createPost(url);
        httpRequest.header("Content-Type", contentType);
        return httpRequest;

    }

    public static JSONObject httpsGet(String url) {
        HttpRequest httpRequest = HttpUtil.createGet(url);
        HttpResponse response = httpRequest.execute();
        return JSONObject.parseObject(response.body());
    }

    public static JSONObject httpsPostJson(String url, String body) {
        String result = HttpUtil.post(url, body);
        return JSONObject.parseObject(result);
    }
}
