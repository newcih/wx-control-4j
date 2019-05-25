package org.newcih.wxapi.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static Map<String, String> xmlToMap(HttpServletRequest request) {
        Map<String, String> resultMap = new HashMap<>();
        SAXReader saxReader = new SAXReader();

        try (InputStream inputStream = request.getInputStream()) {
            Document document = saxReader.read(inputStream);
            Element root = document.getRootElement();
            List<Element> list = root.elements();

            for (Element element : list) {
                resultMap.put(element.getName(), element.getText());
            }

        } catch (Exception e) {
            logger.error("读取XML文档转换成Map对象发生异常", e);
            return resultMap;
        }

        return resultMap;
    }
}
