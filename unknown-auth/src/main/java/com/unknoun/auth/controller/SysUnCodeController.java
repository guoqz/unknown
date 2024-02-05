package com.unknoun.auth.controller;


import com.unknoun.auth.dto.SysUnCodeDto;
import com.unknoun.auth.dto.TokenDTO;
import com.unknoun.auth.entity.SysUnCode;
import com.unknoun.auth.service.SysUnCodeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Created Guoqz
 * @Date 2023-09-24 22:26
 * @Description TODO
 */
@Slf4j
@RestController
@RequestMapping("token")
@Api(tags = "token")
public class SysUnCodeController {

    @Autowired
    private SysUnCodeService tokenService;

    @PostMapping("")
    public String createToken(@RequestBody SysUnCodeDto unCodeDto) {
        return tokenService.createToken(unCodeDto);
    }

    @PostMapping("/get")
    public SysUnCode getToken(@RequestBody TokenDTO tokenDTO) {
        return tokenService.getToken(tokenDTO);
    }

    @PostMapping("/invalid")
    public void invalidToken(@RequestBody TokenDTO tokenDTO) {
        tokenService.invalidToken(tokenDTO);
    }

    @PostMapping("/refresh")
    public void refreshToken(@RequestBody TokenDTO tokenDTO) {
        tokenService.refreshToken(tokenDTO);
    }

}
