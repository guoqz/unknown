package com.unknown.document.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Created Guoqz
 * @Date 2023-09-16 10:41
 * @Description TODO
 */
@Data
@TableName("doc_fileInfo")
public class DocFileInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 目录id
     */
    @TableField("dirId")
    private Integer dirId;

    /**
     * 原文件名
     */
    @TableField("fileName")
    private String fileName;

    /**
     * 文件别名(唯一)
     */
    @TableField("fileAlias")
    private String fileAlias;

    /**
     * 相对路径
     */
    @TableField("relativePath")
    private String relativePath;

    /**
     * 绝对路径
     */
    @TableField("fileSize")
    private Long fileSize;

    /**
     * 是否删除
     */
    @TableField("isDel")
    private String isDel;

    /**
     * 是否禁用
     */
    @TableField("isActive")
    private String isActive;


    /**
     * 创建人
     */
    @TableField("creator")
    private Integer creator;

    /**
     * 创建日期
     */
    @TableField("createDate")
    private String createDate;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private String createTime;

    /**
     * 创建者
     */
    @TableField("updater")
    private Integer updater;

    /**
     * 更新日期
     */
    @TableField("updateDate")
    private String updateDate;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private String updateTime;

}
