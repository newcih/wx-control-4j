package util;

import java.util.function.Predicate;

public class StringUtil {

    public static Predicate<String> isBlank = str -> str == null || "".equals(str);

    public static Predicate<String> notBlank = str -> !isBlank.test(str);
}
