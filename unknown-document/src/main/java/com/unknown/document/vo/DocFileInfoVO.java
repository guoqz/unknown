package com.unknown.document.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "文件展示信息")
public class DocFileInfoVO extends BaseVO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "所属文档")
    private Integer docId;

    @ApiModelProperty(value = "原始文件名")
    private String fileName;

    @ApiModelProperty(value = "文件别名")
    private String fileAlias;

    @ApiModelProperty(value = "文件相对路径")
    private String relativePath;
    @ApiModelProperty(value = "文件绝对(存储)路径")
    private String absolutePath;

    @ApiModelProperty(value = "文件大小")
    private Long fileSize;
    @ApiModelProperty(value = "文件大小(MB)")
    private String fileSizeLabel;

}
