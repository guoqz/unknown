package com.unknown.system.controller;

import com.unknown.common.page.PageInfoR;
import com.unknown.common.page.PageParam;
import com.unknown.common.respone.R;
import com.unknown.system.dto.SysUserInfoDTO;
import com.unknown.system.service.SysUserInfoService;
import com.unknown.system.vo.SysUserInfoPageVO;
import com.unknown.system.vo.SysUserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Created Guoqz
 * @Date 2023-11-04 9:29
 * @Description TODO
 */
@RestController
@RequestMapping("user")
@Api(tags = "用户管理")
public class SysUserInfoController {


    @Autowired
    private SysUserInfoService sysUserInfoService;

    @ApiOperation("分页")
    @PostMapping("page")
    public R<PageInfoR<SysUserInfoPageVO>> page(@RequestBody PageParam param) {
        PageInfoR<SysUserInfoPageVO> result = sysUserInfoService.page(param);
        return R.success(result);
    }


    @PostMapping("ins")
    public R<Integer> insert(@RequestBody SysUserInfoDTO dto) {
        Integer id = sysUserInfoService.insert(dto);
        return R.success(id);
    }

    @PostMapping("del/batch")
    public R<Integer> logicBatchDel(@RequestBody List<Integer> ids) {
        Integer count = sysUserInfoService.logicBatchDel(ids);
        return R.success(count, "删除" + count + "条记录");
    }

    @PostMapping("selby/{id}")
    public R<SysUserInfoVO> getUserInfoById(@PathVariable Integer id) {
        SysUserInfoVO userInfoVO = sysUserInfoService.getUserInfoById(id);
        return R.success(userInfoVO);
    }

    @PostMapping("selby/username")
    public R<SysUserInfoVO> selectInfoByUsername(@RequestParam String username) {
        SysUserInfoVO userInfoVO = sysUserInfoService.selectInfoByUsername(username);
        return R.success(userInfoVO);
    }


}
