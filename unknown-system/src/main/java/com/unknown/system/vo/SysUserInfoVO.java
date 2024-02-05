package com.unknown.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Created Guoqz
 * @Date 2023-09-24 11:02
 * @Description TODO
 */
@Data
@ApiModel(value = "用户信息")
public class SysUserInfoVO extends BaseVO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "身份标识")
    private String identity;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String gender;
    @ApiModelProperty(value = "性别")
    private String genderLabel;

    @ApiModelProperty(value = "生日")
    private String birthday;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "电话号码")
    private String telephone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "住址")
    private String address;

    @ApiModelProperty(value = "头像")
    private String picture;

    @ApiModelProperty(value = "签名")
    private String sign;

}
