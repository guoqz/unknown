package com.unknown.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.unknown.common.enums.GenderEnum;
import com.unknown.common.enums.IsActiveEnum;
import com.unknown.common.enums.IsDelEnum;
import com.unknown.common.enums.UserTypeEnum;
import com.unknown.common.exception.http.CustomException;
import com.unknown.common.enums.http.CustomExceptionType;
import com.unknown.common.page.PageInfoR;
import com.unknown.common.page.PageParam;
import com.unknown.common.page.PageUtil;
import com.unknown.common.utils.DateTimeUtil;
import com.unknown.common.utils.SaltMD5Util;
import com.unknown.system.dto.SysUserInfoDTO;
import com.unknown.system.entity.SysUserInfo;
import com.unknown.system.mapper.SysUserInfoMapper;
import com.unknown.system.vo.SysUserInfoPageVO;
import com.unknown.system.vo.SysUserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * @Created Guoqz
 * @Date 2023-09-23 23:05
 * @Description TODO
 */
@Slf4j
@Service
public class SysUserInfoService {

    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;


    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    public PageInfoR<SysUserInfoPageVO> page(PageParam param) {
        LambdaQueryWrapper<SysUserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserInfo::getIsDel, IsDelEnum.ENABLE.getKey());
        queryWrapper.eq(SysUserInfo::getIsActive, IsActiveEnum.ENABLE.getKey());
        queryWrapper.like(SysUserInfo::getName, param.getKey())
                .or().like(SysUserInfo::getUsername, param.getKey());
        queryWrapper.orderByDesc(SysUserInfo::getId);

        PageHelper.startPage(param.getPage(), param.getRows());
        List<SysUserInfo> list = sysUserInfoMapper.selectList(queryWrapper);
        PageInfo<SysUserInfo> pageInfo = new PageInfo<>(list);

        if (list.isEmpty()) {
            return PageUtil.emptyPage();
        }
        List<SysUserInfoPageVO> voList = list.stream().map(userInfo -> {
            SysUserInfoPageVO vo = new SysUserInfoPageVO();
            BeanUtils.copyProperties(userInfo, vo);
            vo.setGenderLabel(GenderEnum.getValue(userInfo.getGender()));
            return vo;
        }).collect(Collectors.toList());

        PageInfo<SysUserInfoPageVO> voPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo, voPageInfo);
        voPageInfo.setList(voList);
        return PageUtil.pageResult(voPageInfo);
    }

    /**
     * 根据id获取信息
     *
     * @param id 用户id
     * @return vo
     */
    public SysUserInfoVO getUserInfoById(Integer id) {
        SysUserInfo sysUserInfo = sysUserInfoMapper.selectById(id);
        if (null == sysUserInfo) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, "用户不存在");
        }
        return this.toVo(sysUserInfo);
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return vo
     */
    public SysUserInfoVO selectInfoByUsername(String username) {
        SysUserInfo sysUserInfo = sysUserInfoMapper.selectInfoByUsername(username);
        if (null == sysUserInfo) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, "用户不存在");
        }
        return this.toVo(sysUserInfo);
    }


    /**
     * 转义
     *
     * @param sysUserInfo 实体类
     * @return vo
     */
    private SysUserInfoVO toVo(SysUserInfo sysUserInfo) {
        SysUserInfoVO sysUserInfoVO = new SysUserInfoVO();
        BeanUtils.copyProperties(sysUserInfo, sysUserInfoVO);
        sysUserInfoVO.setGenderLabel(GenderEnum.getValue(sysUserInfo.getGender()));
        sysUserInfoVO.setIsDel(IsDelEnum.getValue(sysUserInfo.getIsDel()));
        sysUserInfoVO.setIsActive(IsActiveEnum.getValue(sysUserInfo.getIsActive()));
        sysUserInfoVO.setAge(DateTimeUtil.toAge(sysUserInfo.getBirthday()));
        return sysUserInfoVO;
    }


    /**
     * 新增用户
     *
     * @param dto 信息
     * @return 用户id
     */
    public Integer insert(SysUserInfoDTO dto) {
        SysUserInfo sysUserInfo = new SysUserInfo();
        if (isExistByUsername(dto.getUsername())) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, "用户名已存在");
        }
        BeanUtils.copyProperties(dto, sysUserInfo);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        sysUserInfo.setIdentity(uuid);
        String encodePassword = SaltMD5Util.randomSaltEncode(dto.getPassword());
        sysUserInfo.setPassword(encodePassword);
        sysUserInfo.setType(UserTypeEnum.GENERAL.getKey());
        sysUserInfo.setIsDel(IsDelEnum.ENABLE.getKey());
        sysUserInfo.setIsActive(IsActiveEnum.ENABLE.getKey());
        String date = DateTimeUtil.getDate();
        String time = DateTimeUtil.getTime();
        sysUserInfo.setCreateDate(date);
        sysUserInfo.setCreateTime(time);
        sysUserInfoMapper.insert(sysUserInfo);
        return sysUserInfo.getId();
    }


    public Integer update(Integer id, SysUserInfoDTO dto) {
        return 0;
    }


    /**
     * 批量(逻辑)删除
     *
     * @param ids
     * @return
     */
    public Integer logicBatchDel(List<Integer> ids) {
        SysUserInfo sysUserInfo = new SysUserInfo();
//        sysUserInfo.setIsDel(IsDelEnum.DIS_ENABLE.getKey());
//        QueryWrapper<SysUserInfo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.in(!ids.isEmpty(), "id", ids);
        UpdateWrapper<SysUserInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("isDel", IsDelEnum.DIS_ENABLE.getKey())
                .in(!ids.isEmpty(), "id", ids);
        return sysUserInfoMapper.update(sysUserInfo, updateWrapper);

    }


    /**
     * 根据用户名判断是否存在
     *
     * @param username 用户名
     * @return true存在  false不存在
     */
    public boolean isExistByUsername(String username) {
        int count = sysUserInfoMapper.isExistByUsername(username);
        return count >= 1;
    }


}
