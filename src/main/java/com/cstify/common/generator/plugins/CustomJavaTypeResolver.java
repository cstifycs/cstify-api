package com.cstify.common.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;

public class CustomJavaTypeResolver extends JavaTypeResolverDefaultImpl {
    private static final FullyQualifiedJavaType INSTANT = new FullyQualifiedJavaType("java.time.Instant");

    @Override
    public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn column) {
        if (column.getJdbcType() == Types.TIMESTAMP
                || column.getJdbcType() == Types.TIMESTAMP_WITH_TIMEZONE) {
            return INSTANT;
        }
        return super.calculateJavaType(column);
    }
}
