package com.unknown.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Created Guoqz
 * @Date 2023-09-24 11:12
 * @Description TODO
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {

    MALE("0", "男"),

    FEMALE("1", "女");

    private final String key;
    private final String value;


    /**
     * 根据 key 取 val
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        for (GenderEnum genderEnum : values()) {
            if (genderEnum.getKey().equals(key)) {
                return genderEnum.getValue();
            }
        }
        return null;
    }


}
