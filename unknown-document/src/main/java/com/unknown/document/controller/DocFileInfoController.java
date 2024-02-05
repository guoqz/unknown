package com.unknown.document.controller;

import com.unknown.common.respone.R;
import com.unknown.document.service.DocFileInfoService;
import com.unknown.document.vo.DocFileInfoVO;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @Created GG Bond
 * @Date 2023/9/8 15:12
 * @Description TODO
 */
@RestController
@RequestMapping("doc")
@Api(value = "document", tags = {"文件上传"})
public class DocFileInfoController {

    @Autowired
    private DocFileInfoService docFileInfoService;


    @ApiOperation(value = "上传单个文件")
    @PostMapping("upload")
    public R<DocFileInfoVO> uploadSingleFile(@RequestPart("file") MultipartFile file) {
        DocFileInfoVO fileInfoVO = docFileInfoService.uploadSingleFile(file);
        return R.success(fileInfoVO);
    }

    @ApiOperation(value = "上传多个文件")
    @PostMapping("upload/mul")
    public R<List<DocFileInfoVO>> uploadMultipleFile(@RequestPart("files") List<MultipartFile> files) {
        List<DocFileInfoVO> list = docFileInfoService.uploadMultipleFile(files);
        return R.success(list);
    }


    @GetMapping("tmpUrlByPath")
    @ApiOperation("根据 桶名/relPath 获取文件临时预览下载地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bucketName", value = "存储桶名称, 可选,为空时, 默认存储桶 document"),
            @ApiImplicitParam(name = "relPath", value = "相对地址", required = true)
    })
    public R<String> getFileTmpUrl(@RequestParam(value = "bucketName", required = false) String bucketName,
                                   @RequestParam("relPath") String relPath) {
        String fileTmpUrl = docFileInfoService.getFileTmpUrl(bucketName, relPath);
        return R.success(fileTmpUrl);
    }


    @GetMapping("tmpUrlById/{id}")
    @ApiOperation("根据 文件id 获取文件临时预览下载地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文件id", required = true)
    })
    public R<String> getFileTmpUrl(@PathVariable("id") Integer id) {
        String fileTmpUrl = docFileInfoService.getFileTmpUrlById(id);
        return R.success(fileTmpUrl);
    }


}
