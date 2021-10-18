package com.core.utils;

import java.util.Collections;
import java.util.Map;

public class NullSafetyUtils {

    public static String nullSafetyString(String str) {
        if (str == null) {
            return new String();
        }
        return str;
    }

    public static Map nullSafetyMap(Map map) {
        if (map == null) {
            return Collections.EMPTY_MAP;
        }
        return map;
    }

}
