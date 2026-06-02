package com.cstify.common.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serial;
import java.io.Serializable;

@SuperBuilder
@Getter @Setter @ToString
@AllArgsConstructor
@Schema
public class BaseVo implements Serializable {
	
	@Serial
	private static final long serialVersionUID = -5478476885203093735L;
	
	@JsonIgnore
	@Schema(description = "암호화 키", hidden=true)
    private String dbEncKey;

	@JsonIgnore
	@Schema(description = "언어", hidden=true)
	private String language;

	public BaseVo() {
		this.language = LocaleContextHolder.getLocale().getLanguage();
	}
}
