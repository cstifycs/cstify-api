package com.cstify.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springdoc.core.annotations.ParameterObject;

@SuperBuilder
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@ParameterObject
public class PageRequest {

    @Getter
    @Builder.Default
    @Schema(description = "페이지 번호")
    private Integer page = 1;

    @Getter
    @Builder.Default
    @Schema(description = "페이지 당 데이터 건수")
    private Integer pageSize = 10;
}
