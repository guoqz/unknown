package com.unknown.document.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class FileUtil {


    /**
     * 文件名称判断文件类型
     *
     * @param originalFilename
     * @return
     */
    public static String getFileNameByOrigName(String originalFilename) {
        String[] arr = originalFilename.split("\\.");
        return arr[0];
    }

    /**
     * 文件名称判断文件类型
     *
     * @param originalFilename
     * @return
     */
    public static String getFileTypeByName(String originalFilename) {
        String[] arr = originalFilename.split("\\.");
        return arr[arr.length - 1];
    }


    /**
     * 将文件头转换成16进制字符串
     *
     * @return 16进制字符串
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 得到文件头
     *
     * @return 文件头
     * @throws IOException ex
     */
    private static String getFileContent(InputStream is) throws IOException {
        byte[] b = new byte[28];
        try {
            if (null != is) {
                is.read(b, 0, 28);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytesToHexString(b);
    }


    /**
     * 文件大小格式化
     *
     * @param fileLength 字节
     * @return 文件大小字符串
     */
    public static String getFileSize(long fileLength) {
        DecimalFormat df = new DecimalFormat("#.00");
        String size = "";
        String wrongSize = "0B";
        if (fileLength == 0) {
            return wrongSize;
        }
        if (fileLength < 1024) {
            size = df.format((double) fileLength) + " B";
        } else if (fileLength < 1048576) {
            size = df.format((double) fileLength / 1024) + " KB";
        } else if (fileLength < 1073741824) {
            size = df.format((double) fileLength / 1048576) + " MB";
        } else {
            size = df.format((double) fileLength / 1073741824) + " GB";
        }
        return size;
    }

}
