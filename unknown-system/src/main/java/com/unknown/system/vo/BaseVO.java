package com.unknown.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Created Guoqz
 * @Date 2023-09-16 14:42
 * @Description TODO
 */
@Data
public class BaseVO {

    @ApiModelProperty(value = "删除标记 0正常, 1已删")
    private String isDel;

    @ApiModelProperty(value = "禁用标记 0正常, 1禁用")
    private String isActive;

    @ApiModelProperty(value = "创建人id")
    private Integer creator;
    @ApiModelProperty(value = "创建人名称")
    private String creatorLabel;
    @ApiModelProperty(value = "创建日期")
    private String createDate;
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "修改人id")
    private Integer updater;
    @ApiModelProperty(value = "修改人名称")
    private String updaterLabel;
    @ApiModelProperty(value = "修改日期")
    private String updateDate;
    @ApiModelProperty(value = "修改时间")
    private String updateTime;

}
