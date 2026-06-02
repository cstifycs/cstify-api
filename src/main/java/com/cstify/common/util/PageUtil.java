package com.cstify.common.util;

import com.cstify.common.base.BasePageRequest;
import com.cstify.common.dto.PageDto;
import org.springframework.beans.BeanUtils;

public class PageUtil {
    public static PageDto getPageInfo(BasePageRequest basePageParam){
        PageDto page = new PageDto();
        BeanUtils.copyProperties(basePageParam, page);
        return page;
    }
}
