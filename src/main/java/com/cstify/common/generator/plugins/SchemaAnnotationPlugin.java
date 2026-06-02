package com.cstify.common.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

public class SchemaAnnotationPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field,
                                       TopLevelClass topLevelClass,
                                       IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable,
                                       ModelClassType modelClassType) {
        String comment = introspectedColumn.getRemarks();
        if (comment != null && !comment.isEmpty()) {
            field.addAnnotation("@Schema(description=\"" + comment + "\")");
            topLevelClass.addImportedType("io.swagger.v3.oas.annotations.media.Schema");
        }
        return true;
    }
}
