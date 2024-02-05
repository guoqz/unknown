package com.unknown.common.utils;

import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * @Created Guoqz
 * @Date 2023-09-24 13:55
 * @Description TODO
 */
public class SaltMD5Util {

    private static final String DEFAULT_SALT = "20230909UnknownProjectApproval";


    /**
     * 自定义盐加密
     *
     * @param plaintext 明文
     * @return 盐 + 密文
     */
    public static String defaultSaltEncode(String plaintext) {
        // 盐 + 原密码 + 盐
        return DigestUtils.md5DigestAsHex(
                (DEFAULT_SALT + plaintext + DEFAULT_SALT).getBytes());
    }

    /**
     * 比较
     *
     * @param plaintext  明文
     * @param ciphertext 密文
     * @return true匹配  false不匹配
     */
    public static boolean defaultSaltMatch(String plaintext, String ciphertext) {
        String newPassword = DigestUtils.md5DigestAsHex(
                (DEFAULT_SALT + plaintext + DEFAULT_SALT).getBytes());
        return ciphertext.equals(newPassword);
    }


    /**
     * 随机盐加密
     *
     * @param rawPassword 明文
     * @return 盐 + 密文
     */
    public static String randomSaltEncode(String rawPassword) {
        // 加密过程
        // 1. 使用MD5算法
        // 2. 使用随机的盐值
        // 3. 盐的处理方式为：盐 + 原密码 + 盐 + 原密码 + 盐
        // 注意：因为使用了随机盐，盐值必须被记录下来
        // 随机盐值
        String salt = UUID.randomUUID().toString().replace("-", "");
        // 盐 + 原密码 + 盐 + 原密码 + 盐
        String encodedPassword = DigestUtils.md5DigestAsHex(
                (salt + rawPassword + salt + rawPassword + salt).getBytes());
        // 记录盐值
        return salt + encodedPassword;
    }

    /**
     * 比较
     *
     * @param rawPassword     明文    plaintext
     * @param encodedPassword 密文    ciphertext
     * @return true匹配  false不匹配
     */
    public static boolean randomSaltMatch(String rawPassword, String encodedPassword) {
        // 截取随机盐值
        String salt = encodedPassword.substring(0, 32);

        String newPassword = DigestUtils.md5DigestAsHex(
                (salt + rawPassword + salt + rawPassword + salt).getBytes());
        return encodedPassword.equals(salt + newPassword);
    }

    public static void main(String[] args) {
//        String rawPassword = "123456";
//        String salt = "8743srghsethyftjfcjhvjbbk";
//        String encodedPassword = DigestUtils.md5DigestAsHex(
//                (salt + salt + rawPassword + salt + salt).getBytes());
//        System.out.println("原密码：" + rawPassword);
//        System.out.println("加密后的密码：" + encodedPassword);


//        String encode = randomSaltEncode("123456");
//        System.out.println("encode = " + encode);
//        System.out.println(randomSaltMatch("123456", encode));

        String encode = defaultSaltEncode("123456");
        System.out.println("encode = " + encode);
        System.out.println(defaultSaltMatch("123456", encode));

    }


}
