package com.unknown.system.controller;

import com.unknown.system.bo.FileBO;
import com.unknown.system.service.PictureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Created GG Bond
 * @Date 2023/9/8 15:12
 * @Description TODO
 */
@RestController
@RequestMapping("pic")
@Api(tags = "图片上传")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    /**
     * 图片上传
     *
     * @param file 文件
     * @return 文件信息
     */
    @ApiOperation(value = "图片上传到本地")
    @PostMapping("img")
    public FileBO picUpload(@RequestPart("file") MultipartFile file) {
        return pictureService.picUpload(file);
    }

    /**
     * 获取(加载)图片
     *
     * @param filePath 相对路径     例  2023-09/xxx.jpg
     * @return
     */
    @ApiOperation(value = "获取本地图片")
    @PostMapping("get")
    public void getPic(@RequestParam("filePath") String filePath, HttpServletRequest request, HttpServletResponse response) {
        pictureService.getPic(filePath, request, response);
    }

}
