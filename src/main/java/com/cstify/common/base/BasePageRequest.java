package com.cstify.common.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@SuperBuilder
@Setter
@ToString
@NoArgsConstructor @AllArgsConstructor
public class BasePageRequest extends BaseVo {
	@Serial
	private static final long serialVersionUID = 1427984928046599509L;

	@Getter
	@Builder.Default
    @Schema(description = "페이지 번호")
	private Integer page = 1;
		
	@Getter
	@Builder.Default
    @Schema(description = "페이지 당 데이터 건수")
	private Integer pageSize = 10;
	
	@Getter
	@Builder.Default
    @Schema(description = "총 데이터 건수", hidden=true)
	private Integer totCnt = 0;
//
//	@Builder.Default
//	@Schema(description = "Offset 번호", hidden=true)
//	private Integer offset = 0;
//
//	public Integer getOffset() {
//		if (this.page == 0) {
//			return this.offset;
//		}
//
//		this.offset = (page - 1) * this.pageSize;
//
//		return this.offset;
//	}
}
