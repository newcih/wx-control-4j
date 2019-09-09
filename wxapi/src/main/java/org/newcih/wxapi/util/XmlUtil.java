package org.newcih.wxapi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * XML工具类
 *
 * @author newcih
 */
public class XmlUtil {

    private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);

    /**
     * 将Http请求的XML内容转换为Map对象
     *
     * @param request
     * @return
     */
    public static Map<String, String> xmlBodyToMap(HttpServletRequest request) {
        Map<String, String> resultMap = new HashMap<>();
//        SAXReader saxReader = new SAXReader();
//
//        try (InputStream inputStream = request.getInputStream()) {
//            Document document = saxReader.read(inputStream);
//            Element root = document.getRootElement();
//            List<Element> list = root.elements();
//
//            for (Element element : list) {
//                resultMap.put(element.getName(), element.getText());
//            }
//
//        } catch (Exception e) {
//            logger.error("读取XML文档转换成Map对象发生异常", e);
//            return resultMap;
//        }

        return resultMap;
    }

    public static String xmlBodyToString(HttpServletRequest request) throws IOException {
        try (BufferedReader br = request.getReader()) {
            return br.lines().collect(Collectors.joining());
        } catch (Exception e) {
            logger.error("转换HTTP请求体数据发生异常", e);
            return "";
        }
    }
}
