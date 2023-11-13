package com.base.hibernate.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
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
	
	@Bean
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
