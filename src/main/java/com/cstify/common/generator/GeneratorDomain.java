package com.cstify.common.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GeneratorDomain {
    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;

        InputStream is = GeneratorDomain.class
                .getClassLoader()
                .getResourceAsStream("generator-config.xml");

        if (is == null) {
            throw new IllegalStateException("generator-config.xml not found in classpath");
        }

        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(is);

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator generator = new MyBatisGenerator(config, callback, warnings);
        generator.generate(null);

        System.out.println("✅ MyBatis Generator 완료!");
        warnings.forEach(w -> System.out.println("⚠️ " + w));
    }
}
