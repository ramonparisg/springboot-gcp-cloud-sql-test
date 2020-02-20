package com.ramonparis.cloudsqltest;

import com.google.cloud.sql.core.CoreSocketFactory;
import com.ramonparis.cloudsqltest.utils.CustomCloudSqlJdbc;
import com.ramonparis.cloudsqltest.utils.CustomDatabaseType;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
@Slf4j
public class CloudSqlTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudSqlTestApplication.class, args);
    }

    private static final String CLOUD_SQL_CONNECTION_NAME = "apt-achievment-268700:us-central1:test";
    private static final String DB_USER = "test";
    private static final String DB_PASS = "123456";
    private static final String DB_NAME = "test";

    @Bean
    public DataSource dataSource(){
        HikariConfig config = new HikariConfig();

        // Configure which instance and what database user to connect with.
        config.setJdbcUrl(String.format("jdbc:postgresql:///%s", DB_NAME));
        config.setUsername(DB_USER); // e.g. "root", "postgres"
        config.setPassword(DB_PASS); // e.g. "my-password"

        // For Java users, the Cloud SQL JDBC Socket Factory can provide authenticated connections.
        // See https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory for details.
        config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.postgres.SocketFactory");
        config.addDataSourceProperty("cloudSqlInstance", CLOUD_SQL_CONNECTION_NAME);

        // ... Specify additional connection properties here.

        // [START_EXCLUDE]

        // [START cloud_sql_postgres_servlet_limit]
        // maximumPoolSize limits the total number of concurrent connections this pool will keep. Ideal
        // values for this setting are highly variable on app design, infrastructure, and database.
        config.setMaximumPoolSize(5);
        // minimumIdle is the minimum number of idle connections Hikari maintains in the pool.
        // Additional connections will be established to meet this value unless the pool is full.
        config.setMinimumIdle(5);
        // [END cloud_sql_postgres_servlet_limit]

        // [START cloud_sql_postgres_servlet_timeout]
        // setConnectionTimeout is the maximum number of milliseconds to wait for a connection checkout.
        // Any attempt to retrieve a connection from this pool that exceeds the set limit will throw an
        // SQLException.
        config.setConnectionTimeout(10000); // 10 seconds
        // idleTimeout is the maximum amount of time a connection can sit in the pool. Connections that
        // sit idle for this many milliseconds are retried if minimumIdle is exceeded.
        config.setIdleTimeout(600000); // 10 minutes
        // [END cloud_sql_postgres_servlet_timeout]

        // [START cloud_sql_postgres_servlet_backoff]
        // Hikari automatically delays between failed connection attempts, eventually reaching a
        // maximum delay of `connectionTimeout / 2` between attempts.
        // [END cloud_sql_postgres_servlet_backoff]

        // [START cloud_sql_postgres_servlet_lifetime]
        // maxLifetime is the maximum possible lifetime of a connection in the pool. Connections that
        // live longer than this many milliseconds will be closed and reestablished between uses. This
        // value should be several minutes shorter than the database's timeout value to avoid unexpected
        // terminations.
        config.setMaxLifetime(1800000); // 30 minutes
        // [END cloud_sql_postgres_servlet_lifetime]

        // [END_EXCLUDE]

        // Initialize the connection pool using the configuration object.
        DataSource pool = new HikariDataSource(config);
        // [END cloud_sql_postgres_servlet_create]
        return pool;
    }


//    @Bean
//    public CloudSqlJdbcInfoProvider defaultPostgreSqlJdbcInfoProvider(GcpCloudSqlProperties gcpCloudSqlProperties) {
//        CloudSqlJdbcInfoProvider defaultProvider = new CustomCloudSqlJdbc(gcpCloudSqlProperties, CustomDatabaseType.POSTGRESQL);
//        log.info("Default " + CustomDatabaseType.POSTGRESQL.name() + " JdbcUrl provider. Connecting to " + defaultProvider.getJdbcUrl() + " with driver " + defaultProvider.getJdbcDriverClass());
//        return defaultProvider;
//    }
//


}
