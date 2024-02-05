package com.unknoun.auth.service;


import com.unknoun.auth.dto.LoginDTO;
import com.unknoun.auth.entity.SysUserInfo;
import com.unknoun.auth.mapper.SysUserInfoMapper;
import com.unknown.common.exception.http.CustomException;
import com.unknown.common.enums.http.CustomExceptionType;
import com.unknown.common.utils.SaltMD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Created Guoqz
 * @Date 2023-09-23 23:05
 * @Description TODO
 */
@Slf4j
@Service
public class LoginService {

    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;

    /**
     * 登录
     *
     * @param loginDTO 登录信息
     * @return username
     */
    public String login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        SysUserInfo sysUserInfo = sysUserInfoMapper.selectInfoByUsername(username);
        if (null == sysUserInfo) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, "用户名不存在");
        }
        String password = loginDTO.getPassword();
        boolean comp = SaltMD5Util.randomSaltMatch(password, sysUserInfo.getPassword());
        if (!comp) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, "密码错误");
        }
        return sysUserInfo.getUsername();
    }

}
