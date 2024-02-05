package com.unknown.document.bo;

import lombok.Data;

/**
 * @Created GG Bond
 * @Date 2023/9/8 15:13
 * @Description TODO
 */
@Data
public class FileBO {
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件相对路径
     */
    private String path;

    /**
     * 文件绝对路径
     */
    private String url;
}
