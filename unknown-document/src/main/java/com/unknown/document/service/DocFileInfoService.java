package com.unknown.document.service;

import cn.hutool.core.util.IdUtil;
import com.unknown.common.enums.IsActiveEnum;
import com.unknown.common.enums.IsDelEnum;
import com.unknown.common.utils.DateTimeUtil;
import com.unknown.document.config.MinioConfig;
import com.unknown.document.mapper.DocFileInfoMapper;
import com.unknown.document.entity.DocFileInfo;
import com.unknown.document.utils.DccMinioUtils;
import com.unknown.document.utils.FileUtil;
import com.unknown.document.vo.DocFileInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @Created GG Bond
 * @Date 2023/9/8 15:13
 * @Description TODO
 */

@Slf4j
@Service
public class DocFileInfoService {

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private DccMinioUtils dccMinioUtils;

    @Autowired
    private DocFileInfoMapper docFileInfoMapper;


    /**
     * 上传单个文件
     *
     * @param file
     */
    public DocFileInfoVO uploadSingleFile(MultipartFile file) {
        DocFileInfoVO docFileInfoVO = null;
        DocFileInfo docFileInfo = createDocFileInfo(file);
        log.error("docFileInfo: {}", docFileInfo);
        String objectName = docFileInfo.getRelativePath();
        log.error("objectName: {}", objectName);
        boolean isSuccess = dccMinioUtils.putObject(minioConfig.getDefaultBucketName(), objectName, file);
        if (!isSuccess) {
            log.error("文件上传Minio失败");
            return null;
        }
        try {
            docFileInfoMapper.insert(docFileInfo);
        } catch (Exception e) {
            log.error("数据插入失败, 捕获异常, 删除Minio文件");
            e.printStackTrace();
            dccMinioUtils.removeObject(minioConfig.getDefaultBucketName(), objectName);
            return null;
        }
        docFileInfoVO = this.toVo(docFileInfo);
        log.error("docFileInfoVO: {}", docFileInfoVO);
        return docFileInfoVO;
    }

    /**
     * 上传多个文件
     *
     * @param files
     */
    public List<DocFileInfoVO> uploadMultipleFile(List<MultipartFile> files) {
        List<DocFileInfoVO> list = new ArrayList<>();
        for (MultipartFile file : files) {
            DocFileInfoVO fileInfoVO = this.uploadSingleFile(file);
            if (null != fileInfoVO) {
                list.add(fileInfoVO);
            }
        }
        return list;
    }

    /**
     * 获取文件临时预览下载路径     根据 文件相对地址
     *
     * @param relPath 文件相对地址
     * @return
     */
    public String getFileTmpUrl(String relPath) {
        return getFileTmpUrl(null, relPath);
    }


    /**
     * 获取文件临时预览下载路径     根据 存储桶名 文件相对地址
     *
     * @param bucketName 存储桶名称
     * @param relPath    文件相对地址
     * @return
     */
    public String getFileTmpUrl(String bucketName, String relPath) {
        if (null == bucketName || "".equals(bucketName)) {
            // 参数为空，给默认值
            bucketName = minioConfig.getDefaultBucketName();
        }
        boolean hasFile = dccMinioUtils.hasFile(bucketName, relPath);
        if (hasFile) {
            return dccMinioUtils.preSignedGetObject(bucketName, relPath);
        }
        return "Object does not exist";
    }

    /**
     * 获取文件临时预览下载路径     根据文件id
     *
     * @param id 文件ID
     * @return
     */
    public String getFileTmpUrlById(Integer id) {
        DocFileInfo docFileInfo = docFileInfoMapper.selectById(id);
        String relativePath = docFileInfo.getRelativePath();
        boolean hasFile = dccMinioUtils.hasFile(minioConfig.getDefaultBucketName(), relativePath);
        if (hasFile) {
            return dccMinioUtils.preSignedGetObject(minioConfig.getDefaultBucketName(), relativePath);
        }
        return "Object does not exist";
    }

    /**
     * 通过id获取文件信息
     *
     * @param id id
     * @return 文件信息
     */
    public DocFileInfoVO selectById(Integer id) {
        DocFileInfo docFileInfo = docFileInfoMapper.selectById(id);
        return this.toVo(docFileInfo);
    }

    /**
     * 创建文件存储对象
     *
     * @param file 文件信息
     * @return 文件存储对象
     */
    public DocFileInfo createDocFileInfo(MultipartFile file) {
        DocFileInfo docFileInfo = new DocFileInfo();
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return docFileInfo;
        }
        // 例    433vjt343.jpg
        String fileName = FileUtil.getFileNameByOrigName(originalFilename); // 433vjt343
        String fileType = FileUtil.getFileTypeByName(originalFilename); // jpg
        String fileAlias = IdUtil.fastSimpleUUID() + "." + fileType; // 433vjt343.jpg
        String relativePath = DateTimeUtil.getYearMonth() + "/" + fileAlias; // 2023-09/433vjt343.jpg
        docFileInfo.setFileName(originalFilename);
        docFileInfo.setFileAlias(fileAlias);
        docFileInfo.setRelativePath(relativePath);
        docFileInfo.setFileSize(file.getSize());
        docFileInfo.setIsDel(IsDelEnum.ENABLE.getKey());
        docFileInfo.setIsActive(IsActiveEnum.ENABLE.getKey());
        String date = DateTimeUtil.getDate();
        String time = DateTimeUtil.getTime();
        docFileInfo.setCreateDate(date);
        docFileInfo.setCreateTime(time);
        docFileInfo.setUpdateDate(date);
        docFileInfo.setUpdateTime(time);
        return docFileInfo;
    }


    /**
     * 转义
     *
     * @param dccDocFile 实体类
     * @return 实体类VO
     */
    private DocFileInfoVO toVo(DocFileInfo dccDocFile) {
        DocFileInfoVO fileInfoVO = new DocFileInfoVO();
        BeanUtils.copyProperties(dccDocFile, fileInfoVO);
        String fileSize = FileUtil.getFileSize(dccDocFile.getFileSize());
        fileInfoVO.setFileSizeLabel(fileSize);
        String absolutePath = minioConfig.getEndpoint() + ":" + minioConfig.getPort() + "/" + minioConfig.getDefaultBucketName() + "/" + dccDocFile.getRelativePath();
        fileInfoVO.setAbsolutePath(absolutePath);
        fileInfoVO.setCreatorLabel("");
        fileInfoVO.setUpdaterLabel("");
        return fileInfoVO;
    }


}

