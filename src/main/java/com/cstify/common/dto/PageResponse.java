package com.cstify.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PageResponse<T> {

    @Schema(description = "페이지 번호")
    private PageDto pageInfo;

    @Schema(description = "목록")
    private List<T> data;
}
