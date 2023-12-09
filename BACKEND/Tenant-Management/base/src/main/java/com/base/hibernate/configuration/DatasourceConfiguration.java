package com.base.hibernate.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.base.util.Log;

/**
 * @author Muhil
 *
 */
@Configuration
@EnableTransactionManagement
public class DatasourceConfiguration {
	
	@Value("${spring.datasource.url}")
	private String dsUrl;
	
	@Value("${spring.datasource.username}")
	private String userName;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${spring.datasource.driver-class-name}")
	private String driver;
	
	@Bean("ds")
	//@Primary
    public DataSource getDataSource() {
		Log.base.debug("Initializing primary data source");
        return DataSourceBuilder.create()
          .driverClassName(driver)
          .url(dsUrl)
          .username(userName)
          .password(password)
          .build();	
    }

}
