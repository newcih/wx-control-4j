package util;

import domain.WechatInfo;

import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * @author newcih
 */
public class ObjectUtils {

    public static void printObject(Object object) {
        Method[] methods = object.getClass().getMethods();
        Stream.of(methods).parallel()
                .filter(method -> method.getParameterCount() == 0)
                .forEach(method -> {
                    try {
                        Object result = method.invoke(object);
                        System.out.println(method.getName() + " -> " + result);
                    } catch (Exception ignored) {
                    }
                });
    }

    public static void main(String[] args) {
        WechatInfo wechatInfo = new WechatInfo();
        printObject(wechatInfo);
    }
}
