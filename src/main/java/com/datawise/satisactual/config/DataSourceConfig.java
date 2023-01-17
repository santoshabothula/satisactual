package com.datawise.satisactual.config;

import com.datawise.satisactual.model.ApplicationConfigDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("!local")
@Import(ApplicationConfig.class)
public class DataSourceConfig {

    @Autowired
    private ApplicationConfigDetails details;

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(details.getDBIPAddress());
        dataSourceBuilder.username(details.getDBUsername());
        dataSourceBuilder.password(details.getDBPassword());
        return dataSourceBuilder.build();
    }

}
