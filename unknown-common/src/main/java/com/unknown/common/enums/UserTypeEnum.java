package com.unknown.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Created Guoqz
 * @Date 2023-09-24 12:09
 * @Description TODO
 */

@Getter
@AllArgsConstructor
public enum UserTypeEnum {

    GENERAL("0", "普通"),

    ADMIN("1", "管理员");

    private final String key;
    private final String value;


    /**
     * 根据 key 取 val
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        for (UserTypeEnum userTypeEnum : values()) {
            if (userTypeEnum.getKey().equals(key)) {
                return userTypeEnum.getValue();
            }
        }
        return null;
    }

}
