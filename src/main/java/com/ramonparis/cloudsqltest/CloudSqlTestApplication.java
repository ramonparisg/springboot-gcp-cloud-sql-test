package com.ramonparis.cloudsqltest;

import com.google.cloud.sql.core.CoreSocketFactory;
import com.ramonparis.cloudsqltest.utils.CustomCloudSqlJdbc;
import com.ramonparis.cloudsqltest.utils.CustomDatabaseType;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gcp.autoconfigure.sql.CloudSqlJdbcInfoProvider;
import org.springframework.cloud.gcp.autoconfigure.sql.GcpCloudSqlProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
@Slf4j
public class CloudSqlTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudSqlTestApplication.class, args);
    }


    @Bean
    public CloudSqlJdbcInfoProvider defaultPostgreSqlJdbcInfoProvider(GcpCloudSqlProperties gcpCloudSqlProperties) {
        CloudSqlJdbcInfoProvider defaultProvider = new CustomCloudSqlJdbc(gcpCloudSqlProperties, CustomDatabaseType.POSTGRESQL);
        log.info("Default " + CustomDatabaseType.POSTGRESQL.name() + " JdbcUrl provider. Connecting to " + defaultProvider.getJdbcUrl() + " with driver " + defaultProvider.getJdbcDriverClass());
        return defaultProvider;
    }



}
