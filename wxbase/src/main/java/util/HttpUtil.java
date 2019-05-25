package util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.Random;

/**
 * 网络请求工具类
 *
 * @author NEWCIH
 */
public class HttpUtil {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final Marker MARKER = MarkerFactory.getMarker("HTTP");
    private static OkHttpClient client;
    private static Random requestIdRandom = new Random(System.currentTimeMillis());
    private final static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    static {
        client = new OkHttpClient().newBuilder().build();
    }

    /**
     * POST请求，JSON参数
     *
     * @param param
     * @return
     */
    public static JsonObject postWithJSON(String param, String url) {
        Request.Builder builder = new Request.Builder();
        String requestId = requestIdRandom.nextInt(1000000) + "";
        String responseText;
        if (param != null) {
            RequestBody requestBody = RequestBody.create(JSON, param);
            builder.post(requestBody);
        }
        builder.url(url);

        log.info(MARKER, "[POST-{}]使用参数{}请求{}", requestId, param, url);

        try {
            Response response = client.newCall(builder.build()).execute();
            responseText = response.body().string();

            log.info(MARKER, "[POST-{}]请求结果{}", requestId, responseText);

            return new JsonParser().parse(responseText).getAsJsonObject();
        } catch (Exception e) {
            log.error(MARKER, "[POST-{}]请求发生异常", requestId, e);
            return null;
        }
    }

    /**
     * GET请求
     *
     * @param param JSON请求字符串
     * @param url   链接
     * @return JSON请求结果
     */
    public static JsonObject getWithJSON(JsonObject param, String url) {
        String requestId = requestIdRandom.nextInt(1000000) + "";
        StringBuffer sb = new StringBuffer(url);
        String responseText;

        if (param != null) {
            sb.append(sb.indexOf("?") < 0 ? "?" : "&");
            // 禁止使用并行流，并行流会导致sb拼接的内容顺序错乱
            param.keySet().forEach(p -> sb.append(p).append("=").append(param.get(p).getAsString()).append("&"));
        }

        Request request = new Request.Builder().url(sb.toString()).get().build();
        try {
            log.info(MARKER, "[GET-{}]使用参数{}请求{}", requestId, param, sb.toString());

            Response response = client.newCall(request).execute();
            responseText = response.body().string();

            log.info(MARKER, "[GET-{}]请求结果{}", requestId, responseText);

            return new JsonParser().parse(responseText).getAsJsonObject();
        } catch (Exception e) {
            log.error(MARKER, "[GET-{}]请求发生异常", requestId, e);
            return null;
        }
    }

}
