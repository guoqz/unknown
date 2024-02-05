

package com.unknown.common.page;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;


@Data
public class PageInfoR<T> {

    private Long total;
    private Integer pages;
    private Integer pageNum;
    private Integer pageSize;
    private List<T> list;

    public PageInfoR(PageInfo<T> pageInfo) {
        this.total = pageInfo.getTotal();
        this.pages = pageInfo.getPages();
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.list = pageInfo.getList();
    }

}