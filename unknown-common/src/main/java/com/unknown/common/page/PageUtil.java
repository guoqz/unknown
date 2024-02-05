package com.unknown.common.page;


import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.Collections;

public class PageUtil {

    public static <T> PageInfoR<T> pageResult(PageInfo<T> pageInfo) {
        return new PageInfoR<>(pageInfo);
    }


    public static <T> PageInfoR<T> emptyPage() {
//        return new PageInfoR<>(new PageInfo<T>(Collections.emptyList()));
        return new PageInfoR<>(new PageInfo<T>(new ArrayList<T>()));
    }


}
