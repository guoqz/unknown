package com.unknown.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.unknown.system.entity.SysUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Created Guoqz
 * @Date 2023-09-23 23:02
 * @Description TODO
 */
@Mapper
public interface SysUserInfoMapper extends BaseMapper<SysUserInfo> {

    @Select(" select count(1) from sys_userinfo where username = #{username} and isDel = '0' ")
    int isExistByUsername(String username);

    @Select(" select * from sys_userinfo where username = #{username} and isDel = '0' ")
    SysUserInfo selectInfoByUsername(String username);
}
