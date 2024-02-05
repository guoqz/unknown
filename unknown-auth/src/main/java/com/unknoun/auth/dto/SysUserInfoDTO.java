package com.unknoun.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Created Guoqz
 * @Date 2023-09-24 12:20
 * @Description TODO
 */
@Data
@ApiModel(value = "新增用户模型")
public class SysUserInfoDTO {

    @ApiModelProperty(value = "用户名")
    @NotNull(message = "用户名不能为null")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotNull(message = "密码不能为null")
    @NotBlank(message = "密码不能为空")
    private String password;

}
