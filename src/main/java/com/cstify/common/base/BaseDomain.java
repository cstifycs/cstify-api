package com.cstify.common.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.time.Instant;

@SuperBuilder
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class BaseDomain extends BaseVo {

	@Serial
    private static final long serialVersionUID = -1907175631308742199L;

    private Boolean isDeleted;

    @Schema(description = "생성일시")
    private Instant createdAt;

    @Schema(description = "생성자")
    private Long createdBy;

    @Schema(description = "수정일시")
    private Instant updatedAt;

    @Schema(description = "수정자")
    private Long updatedBy;
}
