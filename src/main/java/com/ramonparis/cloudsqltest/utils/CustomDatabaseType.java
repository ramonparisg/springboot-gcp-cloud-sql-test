package com.ramonparis.cloudsqltest.utils;

/**
 * @author Ramón París
 * @since 19-02-20
 */
public enum CustomDatabaseType {

    MYSQL("com.mysql.jdbc.Driver", "jdbc:mysql://google/%s?socketFactory=com.google.cloud.sql.mysql.SocketFactory&cloudSqlInstance=%s&useSSL=false"),
    POSTGRESQL("org.postgresql.Driver", "jdbc:postgresql://google/%s?socketFactory=com.google.cloud.sql.postgres.SocketFactory&cloudSqlInstance=%s&sslmode=disable&ssl=false");

    private final String jdbcDriverName;
    private final String jdbcUrlTemplate;

    CustomDatabaseType(String jdbcDriverName, String jdbcUrlTemplate) {
        this.jdbcDriverName = jdbcDriverName;
        this.jdbcUrlTemplate = jdbcUrlTemplate;
    }

    public String getJdbcDriverName() {
        return this.jdbcDriverName;
    }

    public String getJdbcUrlTemplate() {
        return this.jdbcUrlTemplate;
    }
}
