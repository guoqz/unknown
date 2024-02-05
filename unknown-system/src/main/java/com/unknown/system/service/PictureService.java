package com.unknown.system.service;


import com.unknown.common.exception.http.CustomException;
import com.unknown.common.enums.http.CustomExceptionType;
import com.unknown.common.utils.DateTimeUtil;
import com.unknown.system.bo.FileBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

/**
 * @Created GG Bond
 * @Date 2023/9/8 15:13
 * @Description TODO
 */
@Service
@Slf4j
public class PictureService {


    // 图片文件类型
    private static final String[] PIC_TYPE = {".jpg", ".png", ".jpeg"};
    // 单个图片文件大小
    private static final int SINGLE_LIMIT = 1024 * 1024 * 10;


    @Value("${unknown.system.file.picPath}")
    private String PIC_PATH;

    /**
     * 获取(加载)图片
     *
     * @param path     相对路径     例  2023-09/xxx.jpg
     * @param request
     * @param response
     */
    public void getPic(String path, HttpServletRequest request, HttpServletResponse response) {
        FileInputStream fis = null;
        ServletOutputStream os = null;
        try {
            File file = new File(PIC_PATH + path);
            if (!file.exists()) {
                log.error("图片不存在");
                return;
            }
            fis = new FileInputStream(PIC_PATH + path);
            os = response.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((count = fis.read(buffer)) != -1) {
                os.write(buffer, 0, count);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("图片获取失败");
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 文件上传
     *
     * @param file 文件
     * @return
     */
    public FileBO picUpload(MultipartFile file) {

        // 检测文件大小
        checkFileLength(file);
        log.error("检查文件大小通过");
        String filename = file.getOriginalFilename();
        assert filename != null;
        String prefixName = filename.substring(0, filename.indexOf("."));// 原文件名(前缀)
        String suffix = filename.substring(filename.lastIndexOf(".")); // 后缀 .jpg
        // 检测文件类型
        checkPicType(suffix);
        log.error("检查文件类型通过");
        String newFilename = UUID.randomUUID().toString().replaceAll("-", "") + suffix; // 新文件名
        log.error("新文件名 {}", newFilename);
        String fDate = DateTimeUtil.getYearMonth(); // 2023-09
        String relativePath = fDate + "/" + newFilename; //  相对路径  2023-09/xxx.jpg
        log.error("相对路径 {}", relativePath);
        String filepath = PIC_PATH + relativePath; // 绝对路径  /var/.../2023-09/xxx.jpg
        log.error("绝对路径 {}", filepath);
        File parenFile = new File(filepath);
        if (!parenFile.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            log.error("目标文件所在的目录不存在，创建父目录");
            parenFile.getParentFile().mkdirs();
        }
        try {
            // 写入磁盘
            log.error("开始写入磁盘");
            file.transferTo(parenFile);
            log.error("写入磁盘成功");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("图片上传失败");
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, "图片上传失败");
        }
        FileBO fileBO = new FileBO();
        fileBO.setName(newFilename);
        fileBO.setPath(relativePath);
        fileBO.setUrl(filepath);
        return fileBO;
    }

    /**
     * 检查文件大小
     */
    protected static void checkFileLength(MultipartFile file) {
        try {
            if (file.getBytes().length > SINGLE_LIMIT) {
                throw new CustomException(CustomExceptionType.SYSTEM_ERROR, "单个图片不超过10M");
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("检查文件大小出错");
        }
    }


    /**
     * 检查文件类型
     */
    protected static void checkPicType(String targetValue) {
        if (!Arrays.asList(PIC_TYPE).contains(targetValue)) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, "仅支持 .jpg .png .jpeg 类型图片");
        }
    }

}
