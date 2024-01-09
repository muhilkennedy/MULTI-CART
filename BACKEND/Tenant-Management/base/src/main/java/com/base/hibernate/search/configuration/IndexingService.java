package com.base.hibernate.search.configuration;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@Configuration
public class IndexingService implements ApplicationListener<ContextRefreshedEvent>{
	
	@Autowired
	private EntityManager entityManager;

	@Transactional("transactionManager")
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		SearchSession searchSes = Search.session(entityManager);
		try {
			searchSes.massIndexer().idFetchSize(10).batchSizeToLoadObjects(5).threadsToLoadObjects(5).startAndWait();
		}
		catch(Exception e) {
			System.out.println(e);
			Thread.currentThread().interrupt();
		}
		
	}

}
