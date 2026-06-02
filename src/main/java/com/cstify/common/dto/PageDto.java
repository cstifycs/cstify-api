package com.cstify.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {

	@Builder.Default
	@Schema(description = "현재 페이지")
	private Integer page = 1;

	@Builder.Default
	@Schema(description = "페이지 사이즈")
	private Integer pageSize = 10;

	@Builder.Default
	@Schema(description = "총 데이터 건수")
	private Integer totCnt = 0;

	@Builder.Default
	@Schema(description = "총 페이지 개수")
	private Integer totPageCnt = 0;

    public static PageDto of(PageRequest params, Integer totCnt) {
        PageDto dto = new PageDto();
        dto.page = params.getPage();
        dto.pageSize = params.getPageSize();
        dto.totCnt = totCnt;
        dto.totPageCnt = (int) Math.ceil((double) totCnt / params.getPageSize());
        return dto;
    }
	
//	public Integer getTotPageCnt() {
//		if(this.totCnt == 0 || this.pageSize == 0) {
//			this.totPageCnt = 0;
//		} else {
//			this.totPageCnt = this.totCnt / this.pageSize + (this.totCnt % this.pageSize == 0 ? 0 : 1);
//		}
//
//		return this.totPageCnt;
//	}
}
