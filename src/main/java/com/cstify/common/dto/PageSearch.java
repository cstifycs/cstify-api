package com.cstify.common.dto;

import com.cstify.common.base.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@SuperBuilder
@Setter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class PageSearch extends BaseVo {
    @Serial
    private static final long serialVersionUID = 7986624167368722176L;

	@Builder.Default
	@Schema(description = "페이징 여부")
	private Boolean isPaging = true;

	@Schema(description = "페이지 번호")
    private Integer page;

	@Schema(description = "페이지 당 데이터 건수")
    private Integer pageSize;

	@Schema(description = "옵셋")
	private Integer offset;

    public Integer getOffset() {
		if (this.page == 0) {
			return this.offset;
		}

		this.offset = (page - 1) * this.pageSize;
		return this.offset;
	}
}
