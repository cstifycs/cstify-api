package com.cstify.common.converter;

import org.jspecify.annotations.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringTrimToNullConverter implements Converter<String, String> {
    @Override
    public @Nullable String convert(String source) {
        if (source == null) return null;
        String trimmed = source.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
