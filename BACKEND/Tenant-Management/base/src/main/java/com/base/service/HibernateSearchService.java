package com.base.service;

import org.hibernate.HibernateException;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.util.Log;

import jakarta.persistence.EntityManager;

/**
 * @author Muhil
 * runs hibernate search mass indexing
 */
@Service
public class HibernateSearchService {

	private static final int LOAD_THREADS_COUNT = 2;
	private static final int LOAD_BATCH_SIZE = 1000;

	@Autowired
	private EntityManager entityManager;

	@Transactional("transactionManager")
	public void performIndexing() {
		SearchSession searchSes = Search.session(entityManager);
		try {
			searchSes.massIndexer().idFetchSize(LOAD_BATCH_SIZE).batchSizeToLoadObjects(LOAD_BATCH_SIZE)
					.threadsToLoadObjects(LOAD_THREADS_COUNT).startAndWait();
		} catch (Exception e) {
			Log.base.error("Exception on hibernate indexing : {}", e);
			throw new HibernateException(e);
		}
	}

}
