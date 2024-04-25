package com.base.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.server.BaseSession;
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
	
	/**
	 * @param clz entity class
	 * @param value search value
	 * @param limit result limit
	 * @param searchFieldNames field names to perform search on.
	 * @return list of objects based on fuzzy search logic. Default error acceptance is 2. 
	 */
	public List<?> fuzzySearch(Class<?> clz, String value, int limit, String... searchFieldNames) {
		return Search.session(entityManager).search(clz)
				.where(field -> field.and(
				field.match().fields(searchFieldNames).matching(value).fuzzy(),
				field.match().fields("tenantid").matching(BaseSession.getTenantId()))).fetch(limit).hits();
	}

	/*public List<?> boostSearch(Class<?> clz, String value, String value2, int limit, String... searchFieldNames) {
		return Search.session(entityManager).search(clz)
				.where(field -> field.or().with( and -> {
					and.add(field.match().fields(searchFieldNames).matching(value).boost(2));
					and.add(field.match().fields("tenantid").matching(BaseSession.getTenantId()));
				}	
				).with( or -> {
							or.add(field.match().fields("emailid").matching(value2));
						})).fetch(limit).hits();
	}*/

}
