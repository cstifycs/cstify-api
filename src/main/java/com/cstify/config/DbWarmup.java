package com.cstify.config;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Slf4j
@Component
public class DbWarmup implements ApplicationRunner {
    private final DataSource dataSource;

    public DbWarmup(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(@NonNull ApplicationArguments args) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT 1")) {
                ps.execute();
                log.info("DB connection warmup complete");
            }
        }
    }
}
