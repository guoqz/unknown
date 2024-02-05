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
public enum IsActiveEnum {

    ENABLE("0", "正常"),

    DIS_ENABLE("1", "禁用");

    private final String key;
    private final String value;


    /**
     * 根据 key 取 val
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        for (IsActiveEnum activeEnum : values()) {
            if (activeEnum.getKey().equals(key)) {
                return activeEnum.getValue();
            }
        }
        return null;
    }
}
