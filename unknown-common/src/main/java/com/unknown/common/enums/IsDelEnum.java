package com.unknown.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Created Guoqz
 * @Date 2023/5/31 17:09
 * @Description
 */
@Getter
@AllArgsConstructor
public enum IsDelEnum {

    ENABLE("0", "正常"),

    DIS_ENABLE("1", "已删");

    private final String key;
    private final String value;


    /**
     * 根据 key 取 val
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        for (IsDelEnum delEnum : values()) {
            if (delEnum.getKey().equals(key)) {
                return delEnum.getValue();
            }
        }
        return null;
    }

}
