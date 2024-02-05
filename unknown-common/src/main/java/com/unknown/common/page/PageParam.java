package com.unknown.common.page;

import lombok.Data;

@Data
public class PageParam {

    private Integer page;

    private Integer rows;

    private String key;

    public PageParam() {
        this.page = this.page == null ? 1 : this.page;
        this.rows = this.rows == null ? 10 : this.rows;
        this.key = this.key == null ? "" : this.key;
    }

}
