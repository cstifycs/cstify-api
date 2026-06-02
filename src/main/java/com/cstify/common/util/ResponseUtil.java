package com.cstify.common.util;

import com.cstify.common.base.BasePageRequest;
import com.cstify.common.dto.ListResponse;
import com.cstify.common.dto.PageDto;
import com.cstify.common.dto.PageResponse;
import com.cstify.common.dto.SingleResponse;

import java.util.List;

public class ResponseUtil {
    public static <T> PageResponse<T> pageResponse(BasePageRequest param, List<T> dataList) {
        return PageResponse.<T>builder()
                .pageInfo(PageUtil.getPageInfo(param))
                .data(dataList)
                .build();
    }

    public static <T> PageResponse<T> pageResponse(PageDto pageDto, List<T> dataList) {
        return PageResponse.<T>builder()
                .pageInfo(pageDto)
                .data(dataList)
                .build();
    }

    public static <T> ListResponse<T> listResponse(List<T> dataList) {
        return ListResponse.<T>builder()
                .data(dataList)
                .build();
    }

    public static <T> SingleResponse<T> singleResponse(T data) {
        return SingleResponse.<T>builder()
                .data(data)
                .build();
    }
}
