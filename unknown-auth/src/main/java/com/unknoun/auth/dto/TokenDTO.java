package com.unknoun.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Created Guoqz
 * @Date 2023-09-24 22:37
 * @Description TODO
 */
@Data
public class TokenDTO {

    @ApiModelProperty(value = "token")
    private String token;

}
