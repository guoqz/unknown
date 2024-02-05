package com.unknoun.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.unknoun.auth.dto.SysUnCodeDto;
import com.unknoun.auth.dto.TokenDTO;
import com.unknoun.auth.entity.SysUnCode;
import com.unknoun.auth.mapper.SysUnCodeMapper;
import com.unknown.common.enums.IsActiveEnum;
import com.unknown.common.enums.IsDelEnum;
import com.unknown.common.utils.DateTimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @Created Guoqz
 * @Date 2023-09-24 22:24
 * @Description TODO
 */
@Service
public class SysUnCodeService {

    @Autowired
    private SysUnCodeMapper sysUnCodeMapper;


    public String createToken(SysUnCodeDto sysUnCodeDto) {
        SysUnCode sysUnCode = new SysUnCode();
        BeanUtils.copyProperties(sysUnCodeDto, sysUnCode);
        sysUnCode.setIsDel(IsDelEnum.ENABLE.getKey());
        sysUnCode.setIsActive(IsActiveEnum.ENABLE.getKey());
        String date = DateTimeUtil.getDate();
        String time = DateTimeUtil.getTime();
        sysUnCode.setCreateDate(date);
        sysUnCode.setCreateTime(time);
        sysUnCode.setUpdateDate(date);
        sysUnCode.setUpdateTime(time);
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        sysUnCode.setToken(token);
        long currentTime = System.currentTimeMillis();
        currentTime += 30 * 60 * 1000;
        sysUnCode.setExpire(new Date(currentTime));
        sysUnCodeMapper.insert(sysUnCode);
        return sysUnCode.getToken();
    }

    public SysUnCode getToken(TokenDTO tokenDTO) {
        String token = tokenDTO.getToken();
        if (null == token) {
            return new SysUnCode();
        }
        LambdaQueryWrapper<SysUnCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(SysUnCode::getToken, token)
                .eq(SysUnCode::getIsDel, IsDelEnum.ENABLE.getKey())
                .eq(SysUnCode::getIsActive, IsActiveEnum.ENABLE.getKey());
        return sysUnCodeMapper.selectOne(queryWrapper);
    }


    /**
     * 判断是否存在 token
     *
     * @param token token
     * @return true 存在  false 不存在
     */
    private boolean isExistToken(String token) {
        LambdaQueryWrapper<SysUnCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(SysUnCode::getToken, token)
                .eq(SysUnCode::getIsDel, IsDelEnum.ENABLE.getKey())
                .eq(SysUnCode::getIsActive, IsActiveEnum.ENABLE.getKey());
        SysUnCode sysUnCode = sysUnCodeMapper.selectOne(queryWrapper);
        return null != sysUnCode;
    }

    public void invalidToken(TokenDTO tokenDTO) {
        SysUnCode sysUnCode = this.getToken(tokenDTO);
        if (null == sysUnCode) {
            return;
        }
        String date = DateTimeUtil.getDate();
        String time = DateTimeUtil.getTime();
        sysUnCode.setUpdateDate(date);
        sysUnCode.setUpdateTime(time);
        sysUnCode.setIsActive(IsActiveEnum.DIS_ENABLE.getKey());
        sysUnCode.setExpire(new Date());
        sysUnCodeMapper.updateById(sysUnCode);
    }

    public void refreshToken(TokenDTO tokenDTO) {
        SysUnCode sysUnCode = this.getToken(tokenDTO);
        if (null == sysUnCode) {
            return;
        }
        String date = DateTimeUtil.getDate();
        String time = DateTimeUtil.getTime();
        sysUnCode.setUpdateDate(date);
        sysUnCode.setUpdateTime(time);
        long currentTime = System.currentTimeMillis();
        currentTime += 30 * 60 * 1000;
        sysUnCode.setExpire(new Date(currentTime));
        sysUnCodeMapper.updateById(sysUnCode);
    }
}
