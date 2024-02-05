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
@ApiModel(value = "用户分页信息")
public class SysUserInfoPageVO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "性别")
    private String gender;
    private String genderLabel;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

}
