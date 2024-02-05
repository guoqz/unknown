package com.unknown.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Created Guoqz
 * @Date 2023-09-23 22:34
 * @Description
 */

@Data
@TableName("sys_userInfo")
public class SysUserInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 用户名，登录名
     */
    @TableField("username")
    private String username;

    /**
     * 登录密码
     */
    @TableField("password")
    private String password;

    /**
     * 唯一身份标识
     */
    @TableField("identity")
    private String identity;

    /**
     * 性别  0男  1女
     */
    @TableField("gender")
    private String gender;

    /**
     * 生日
     */
    @TableField("birthday")
    private String birthday;

    /**
     * 手机号码
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 电话号码
     */
    @TableField("telephone")
    private String telephone;


    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 住址
     */
    @TableField("address")
    private String address;

    /**
     * 用户头像
     */
    @TableField("picture")
    private String picture;

    /**
     * 签名/座右铭
     */
    @TableField("sign")
    private String sign;

    /**
     * 用户类型     0普通用户   1管理员(角色)
     */
    @TableField("type")
    private String type;


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
