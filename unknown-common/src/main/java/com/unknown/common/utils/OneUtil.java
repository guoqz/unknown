package com.unknown.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Guoqz
 * @version 1.0
 * @date 2023/7/6
 * @description
 */
public class OneUtil {

    /**
     * @param stringBuilder 原字符串
     * @param keyword       关键字
     * @param before        关键字前插入的字符串
     * @param later         关键字前插入的字符串
     * @returnl
     */
    public static String ReplacementInfo(StringBuilder stringBuilder, String keyword, String before, String later) {
        //字符第一次出现的位置
        int index = stringBuilder.indexOf(keyword);
        while (index != -1) {
            stringBuilder.insert(index, before);
            stringBuilder.insert(index + before.length() + keyword.length(), later);
            //下一次出现的位置，
            index = stringBuilder.indexOf(keyword, index + before.length() + keyword.length() + later.length() - 1);
        }
        return stringBuilder.toString();
    }


    /**
     * 将map中的所有key转化为小写
     *
     * @param map
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Description
     */
    public static Map<String, Object> transformLowerCase(Map<String, Object> map) {
        Map<String, Object> resultMap = new HashMap<>();
        if (map == null || map.isEmpty()) {
            return resultMap;
        }
        Set<String> sets = map.keySet();
        for (String key : sets) {
            resultMap.put(key.toLowerCase(), map.get(key));
        }
        return resultMap;
    }

    /**
     * 将map中的所有key转化为大写
     *
     * @param map
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Description
     */
    public static Map<String, Object> transformUpperCase(Map<String, Object> map) {
        Map<String, Object> resultMap = new HashMap<>();
        if (map == null || map.isEmpty()) {
            return resultMap;
        }
        Set<String> sets = map.keySet();
        for (String key : sets) {
            resultMap.put(key.toUpperCase(), map.get(key));
        }
        return resultMap;
    }
}
