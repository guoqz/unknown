package com.unknown.document.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.PutObjectOptions;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.DeleteError;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Created Guoqz
 * @Date 2023-09-16 10:41
 * @Description TODO
 */

@Slf4j
@Component
public class DccMinioUtils {

    @Autowired
    public MinioClient minioClient;

    private static final int DEFAULT_EXPIRY_TIME = 7 * 24 * 3600;


    /**
     * 检查存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return true存在 false不存在
     */
    public boolean bucketExists(String bucketName) {
        try {
            boolean exists = minioClient.bucketExists(bucketName);
            if (!exists) {
                log.error("目标存储桶不存在, 请先创建桶");
            }
            return exists;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("检查桶错误");
            return false;
        }
    }


    /**
     * 列出存储桶中的所有对象名称
     *
     * @param bucketName 存储桶名称
     * @return 文件名
     */
    public List<String> listObjectNames(String bucketName) {
        List<String> listObjectNames = new ArrayList<>();
        boolean flag = bucketExists(bucketName);
        if (flag) {
            Iterable<Result<Item>> myObjects = listObjects(bucketName);
            for (Result<Item> result : myObjects) {
                try {
                    Item item = result.get();
                    listObjectNames.add(item.objectName());
                } catch (Exception e) {
                    if (log.isWarnEnabled()) {
                        log.warn("该桶内存在无法读取数据，请检查异常！", e);
                    }
                }
            }
        }
        return listObjectNames;
    }

    /**
     * 列出存储桶中的所有对象
     *
     * @param bucketName 存储桶名称
     * @return 桶内信息
     */
    public Iterable<Result<Item>> listObjects(String bucketName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            try {
                return minioClient.listObjects(bucketName);
            } catch (XmlParserException e) {
                if (log.isWarnEnabled()) {
                    log.warn("该桶内存在无法读取数据，请检查异常！", e);
                }
            }
        }
        return null;
    }

    /**
     * 判断文件是否存在
     *
     * @param bucketName 桶名称
     * @param objectName 文件名称
     * @return true存在 false不存在
     */
    public boolean hasFile(String bucketName, String objectName) {
        try {
            ObjectStat stat = statObject(bucketName, objectName);
            return stat != null;
        } catch (Exception e) {
            if (log.isWarnEnabled()) {
                log.warn("指定文件不存在 bucket = {}, object = {}", bucketName, objectName, e);
            }
            return false;
        }
    }

    /**
     * 上传本地文件
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称（该名称可包含目录路径，  以“\”分隔）
     * @param fileName   本地文件路径
     * @return true成功  false失败
     */
    public boolean putObject(String bucketName, String objectName, String fileName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            try {
                minioClient.putObject(bucketName, objectName, fileName, null);
                return hasFile(bucketName, objectName);
            } catch (Exception e) {
                if (log.isWarnEnabled()) {
                    log.warn("上传文件失败 bucket = {}, object = {}", bucketName, objectName, e);
                }
                return false;
            }
        }
        return false;
    }

    /**
     * 上传传递的前端文件
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称（该名称可包含目录路径，  以“\”分隔）
     * @param file       文件
     * @return true成功  false失败
     */
    public boolean putObject(String bucketName, String objectName, MultipartFile file) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            InputStream inputStream = null;
            try {
                // 文件大小未知时 参数一：objectSize  传 -1
                PutObjectOptions putObjectOptions = new PutObjectOptions(-1, PutObjectOptions.MIN_MULTIPART_SIZE);
                putObjectOptions.setContentType(file.getContentType());
                inputStream = file.getInputStream();
                minioClient.putObject(bucketName, objectName, inputStream, putObjectOptions);
                inputStream.close();
                return true;
            } catch (Exception e) {
                if (log.isWarnEnabled()) {
                    log.warn("上传文件失败 bucket = {}, object = {}", bucketName, objectName, e);
                }
                return false;
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 上传文件流
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param stream     要上传的流
     * @return true成功  false失败
     */
    public boolean putObject(String bucketName, String objectName, InputStream stream) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            try {
                minioClient.putObject(bucketName, objectName, stream, new PutObjectOptions(stream.available(), -1));
                return hasFile(bucketName, objectName);
            } catch (Exception e) {
                if (log.isWarnEnabled()) {
                    log.warn("上传文件失败 bucket = {}, object = {}", bucketName, objectName, e);
                }
                return false;
            }
        }
        return false;
    }

    /**
     * 获取文件流
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return 文件流
     */
    public InputStream getObject(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            ObjectStat stat = statObject(bucketName, objectName);
            try {
                if (stat != null) {
                    return minioClient.getObject(bucketName, objectName);
                }
            } catch (Exception e) {
                if (log.isWarnEnabled()) {
                    log.warn("获取文件失败 bucket = {}, object = {}", bucketName, objectName, e);
                }
                return null;
            }
        }
        return null;
    }

    /**
     * 以流的形式获取一个文件对象（断点下载）
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param offset     起始字节的位置
     * @param length     要读取的长度 (可选，如果无值则代表读到文件结尾)
     * @return 断点的流
     */
    public InputStream getObject(String bucketName, String objectName, long offset, Long length) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            ObjectStat stat = statObject(bucketName, objectName);
            try {
                if (stat != null) {
                    return minioClient.getObject(bucketName, objectName, offset, length);
                }
            } catch (Exception e) {
                if (log.isWarnEnabled()) {
                    log.warn("获取文件失败 bucket = {}, object = {}", bucketName, objectName, e);
                }
                return null;
            }
        }
        return null;
    }

    /**
     * 下载并将文件保存到本地
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param fileName   本地文件地址
     * @return true成功  false失败
     */
    public boolean getObject(String bucketName, String objectName, String fileName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            ObjectStat stat = statObject(bucketName, objectName);
            try {
                if (stat != null) {
                    minioClient.getObject(bucketName, objectName, fileName);
                    return true;
                }
            } catch (Exception e) {
                if (log.isWarnEnabled()) {
                    log.warn("获取文件失败 bucket = {}, object = {}", bucketName, objectName, e);
                }
                return false;
            }

        }
        return false;
    }

    /**
     * 删除一个对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     */
    public boolean removeObject(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            try {
                minioClient.removeObject(bucketName, objectName);
                return true;
            } catch (Exception e) {
                if (log.isWarnEnabled()) {
                    log.warn("删除文件失败 bucket = {}, object = {}", bucketName, objectName, e);
                }
                return false;
            }
        }
        return false;
    }

    /**
     * 删除指定桶的多个文件对象,返回删除错误的对象列表，全部删除成功，返回空列表
     *
     * @param bucketName  存储桶名称
     * @param objectNames 含有要删除的多个object名称的迭代器对象
     * @return 已删除数据
     */
    public List<String> removeObject(String bucketName, List<String> objectNames) {
        List<String> deleteErrorNames = new ArrayList<>();
        boolean flag = bucketExists(bucketName);
        if (flag) {
            try {
                Iterable<Result<DeleteError>> results = minioClient.removeObjects(bucketName, objectNames);
                for (Result<DeleteError> result : results) {
                    DeleteError error = result.get();
                    deleteErrorNames.add(error.objectName());
                }
            } catch (Exception e) {
                if (log.isWarnEnabled()) {
                    log.warn("删除文件失败 bucket = {}", bucketName, e);
                }
            }

        }
        return deleteErrorNames;
    }

    /**
     * 生成一个给HTTP GET请求用的 preSigned URL。
     * 浏览器/移动端的客户端可以用这个URL进行下载，即使其所在的存储桶是私有的。这个 preSigned URL可以设置一个失效时间，默认值是7天。
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param expires    失效时间（以秒为单位），默认是7天，不得大于七天
     * @return preSigned URL。
     */
    public String preSignedGetObject(String bucketName, String objectName, Integer expires) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            try {
                if (expires < 1 || expires > DEFAULT_EXPIRY_TIME) {
                    throw new InvalidExpiresRangeException(expires,
                            "expires must be in range of 1 to " + DEFAULT_EXPIRY_TIME);
                }
                return minioClient.presignedGetObject(bucketName, objectName, expires);
            } catch (Exception e) {
                if (log.isWarnEnabled()) {
                    log.error("生成GET方式 preSigned URL请求失败", e);
                }
            }
        }
        return "";
    }

    /**
     * 生成一个给HTTP GET请求用的 preSigned URL. 失效时间,默认值是7天.
     * 浏览器/移动端的客户端可以用这个URL进行下载，即使其所在的存储桶是私有的。这个 preSigned URL可以设置一个失效时间，默认值是7天。
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return preSigned URL。
     */
    @SneakyThrows
    public String preSignedGetObject(String bucketName, String objectName) {
        return preSignedGetObject(bucketName, objectName, DEFAULT_EXPIRY_TIME);
    }


    /**
     * 生成一个给HTTP PUT请求用的 preSigned URL。
     * 浏览器/移动端的客户端可以用这个URL进行上传，即使其所在的存储桶是私有的。这个 preSigned URL可以设置一个失效时间，默认值是7天。
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param expires    失效时间（以秒为单位），默认是7天，不得大于七天
     * @return
     */
    @SneakyThrows
    public String preSignedPutObject(String bucketName, String objectName, Integer expires) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            try {
                if (expires < 1 || expires > DEFAULT_EXPIRY_TIME) {
                    throw new InvalidExpiresRangeException(expires,
                            "expires must be in range of 1 to " + DEFAULT_EXPIRY_TIME);
                }
                return minioClient.presignedPutObject(bucketName, objectName, expires);
            } catch (Exception e) {
                if (log.isWarnEnabled()) {
                    log.error("生成PUT方式 preSigned URL请求失败", e);
                }
            }
        }
        return "";
    }


    /**
     * 获取对象的元数据
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return 对象的元数据
     */
    public ObjectStat statObject(String bucketName, String objectName) {
        try {
            ObjectStat stat = minioClient.statObject(bucketName, objectName);
            log.info("stat : {}", stat);
            if (stat != null && stat.length() >= 0) {
                return stat;
            }
            return null;
        } catch (Exception e) {
            if (log.isWarnEnabled()) {
                log.warn("指定文件不存在 bucket = {}, object = {}", bucketName, objectName, e);
            }
            return null;
        }
    }

    /**
     * 文件访问路径
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return 文件访问路径
     */
    public String getObjectUrl(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            try {
                return minioClient.getObjectUrl(bucketName, objectName);
            } catch (Exception e) {
                if (log.isWarnEnabled()) {
                    log.warn("获取文件访问路径失败 bucket = {}, object = {}", bucketName, objectName, e);
                }
                e.printStackTrace();
            }
        }
        return "";
    }

    @Deprecated
    public void downloadFile(String bucketName, String fileName, String originalName, HttpServletResponse response) {
        InputStream file;
        try {
            ObjectStat stat = minioClient.statObject(bucketName, fileName);
            file = minioClient.getObject(bucketName, fileName);
            if (StringUtils.isNotEmpty(originalName)) {
                fileName = originalName;
            }
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(file);
                 ServletOutputStream outputStream = response.getOutputStream()
            ) {
                response.setContentType(stat.contentType());
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
                byte[] b = new byte[4096];
                int len;
                while ((len = bufferedInputStream.read(b)) != -1) {
                    outputStream.write(b, 0, len);
                }
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
