package com.datawise.satisactual.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("local")
@Import(ApplicationConfig.class)
public class DataSourceLocalConfig {

    @Value("${application.datasource.uri}")
    private String dbUri;

    @Value("${application.datasource.username}")
    private String username;

    @Value("${application.datasource.password}")
    private String password;

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(dbUri);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
}
