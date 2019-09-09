/**
 * 对公众平台发送给公众账号的消息加解密示例代码.
 *
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

package org.newcih.wxapi.util;

import com.qq.weixin.mp.aes.AesException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * XMLParse class
 * <p>
 * 提供提取消息格式中的密文及生成回复消息格式的接口.
 */
public class XMLParse {

    /**
     * 提取出xml数据包中的加密消息
     *
     * @param xmltext 待提取的xml字符串
     * @return 提取出的加密消息字符串
     * @throws AesException
     */
//    public static Object[] extract(String xmltext) {
//        Object[] result = new Object[3];
//        try {
//            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
//            dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
//            dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
//            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
//            dbf.setXIncludeAware(false);
//            dbf.setExpandEntityReferences(false);
//            DocumentBuilder db = dbf.newDocumentBuilder();
//            StringReader sr = new StringReader(xmltext);
//            InputSource is = new InputSource(sr);
//            Document document = db.parse(is);
//
//            Element root = document.getDocumentElement();
//            NodeList nodelist1 = root.getElementsByTagName("Encrypt");
//            NodeList nodelist2 = root.getElementsByTagName("ToUserName");
//
//            xmlToMap(document);
//            ObjectUtils.printObject(root);
//
//            result[0] = 0;
//            result[1] = nodelist1.item(0).getTextContent();
//            result[2] = nodelist2.item(0).getTextContent();
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public static Map<String, String > extract(String xmltext) {
        Object[] result = new Object[3];
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            dbf.setXIncludeAware(false);
            dbf.setExpandEntityReferences(false);
            DocumentBuilder db = dbf.newDocumentBuilder();
            StringReader sr = new StringReader(xmltext);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);
            Element root = document.getDocumentElement();
            return xmlToMap(root);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, String> xmlToMap(Element element) {
        NodeList nodeList = element.getChildNodes();
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                result.put(nodeList.item(i).getNodeName(), nodeList.item(i).getTextContent());
            }
        }
        return result;
    }

    /**
     * 生成xml消息
     *
     * @param encrypt   加密后的消息密文
     * @param signature 安全签名
     * @param timestamp 时间戳
     * @param nonce     随机字符串
     * @return 生成的xml字符串
     */
    public static String generate(String encrypt, String signature, String timestamp, String nonce) {

        String format = "<xml>\n" + "<Encrypt><![CDATA[%1$s]]></Encrypt>\n"
                + "<MsgSignature><![CDATA[%2$s]]></MsgSignature>\n"
                + "<TimeStamp>%3$s</TimeStamp>\n" + "<Nonce><![CDATA[%4$s]]></Nonce>\n" + "</xml>";
        return String.format(format, encrypt, signature, timestamp, nonce);

    }
}
