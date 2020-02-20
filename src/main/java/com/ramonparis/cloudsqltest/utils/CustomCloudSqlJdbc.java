package com.ramonparis.cloudsqltest.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gcp.autoconfigure.sql.CloudSqlJdbcInfoProvider;
import org.springframework.cloud.gcp.autoconfigure.sql.GcpCloudSqlProperties;

/**
 * @author Ramón París
 * @since 19-02-20
 */
@Slf4j
public class CustomCloudSqlJdbc implements CloudSqlJdbcInfoProvider {

    private final GcpCloudSqlProperties properties;
    private final CustomDatabaseType databaseType;

    public CustomCloudSqlJdbc(GcpCloudSqlProperties properties, CustomDatabaseType databaseType) {
        this.properties = properties;
        this.databaseType = databaseType;
    }

    @Override
    public String getJdbcDriverClass() {
        return this.databaseType.getJdbcDriverName();
    }

    @Override
    public String getJdbcUrl() {
        return String.format(this.databaseType.getJdbcUrlTemplate(), this.properties.getDatabaseName(), this.properties.getInstanceConnectionName());
    }
}
