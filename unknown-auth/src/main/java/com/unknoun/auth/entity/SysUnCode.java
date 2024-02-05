package com.unknoun.auth.entity;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Created Guoqz
 * @Date 2023-09-24 22:19
 * @Description TODO
 */
@Data
@TableName("sys_unCode")
public class SysUnCode {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * token
     */
    @TableField("token")
    private String token;

    /**
     * token到期时间
     */
    @TableField("expire")
    private Date expire;

    /**
     * 用户id
     */
    @TableField("userid")
    private Integer userid;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

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
