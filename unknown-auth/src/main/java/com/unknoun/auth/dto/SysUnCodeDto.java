package com.unknoun.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Created Guoqz
 * @Date 2023-09-24 22:33
 * @Description TODO
 */
@Data
public class SysUnCodeDto {

    @ApiModelProperty(value = "用户id")
    private Integer userid;

    @ApiModelProperty(value = "用户名")
    private String username;

}
