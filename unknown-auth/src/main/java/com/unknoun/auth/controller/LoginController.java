package com.unknoun.auth.controller;

import com.unknoun.auth.dto.LoginDTO;
import com.unknoun.auth.service.LoginService;
import com.unknown.common.respone.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Created Guoqz
 * @Date 2023-09-23 14:00
 * @Description TODO
 */

@Slf4j
@RestController
@RequestMapping("")
@Api(tags = "登录")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "登录")
    @PostMapping(value = "/login")
    public R<String> login(@RequestBody LoginDTO loginDTO) {
        log.info("loginDTO: {}", loginDTO);
        String username = loginService.login(loginDTO);
        log.info("username: {}", username);
        return R.success(username);
    }


}

