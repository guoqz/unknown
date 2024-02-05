package com.unknoun.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Created Guoqz
 * @Date 2023-09-24 12:20
 * @Description TODO
 */
@Data
@ApiModel(value = "登录参数")
public class LoginDTO {

    @ApiModelProperty(value = "用户名 | 手机号 | 邮箱")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;

}
