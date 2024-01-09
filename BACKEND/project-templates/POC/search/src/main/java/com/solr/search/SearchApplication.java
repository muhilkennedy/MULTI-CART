package com.solr.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(repositoryBaseClass = JpaSpecificationExecutorWithProjectionImpl.class)
@EnableJpaRepositories(basePackages = "com.solr")
//@EnableSolrRepositories(basePackages = "com.solr")
@ComponentScan(basePackages = "com.solr")
@EntityScan(basePackages = "com.solr")
@EnableAutoConfiguration
//@EnableTransactionManagement
public class SearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchApplication.class, args);
	}

}
