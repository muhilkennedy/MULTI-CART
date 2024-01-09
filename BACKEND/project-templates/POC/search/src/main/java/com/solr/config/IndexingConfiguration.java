//package com.solr.config;
//
//import javax.persistence.EntityManager;
//
//import org.springframework.beans.factory.annotation.Autowired;
////import org.apache.solr.client.solrj.SolrClient;
////import org.apache.solr.client.solrj.impl.HttpSolrClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
////import org.springframework.data.solr.core.SolrOperations;
////import org.springframework.data.solr.core.SolrTemplate;
//
//@Configuration
////@EnableSolrRepositories(basePackages = "com.solr")
////@EnableSolrRepositories(basePackages = "com.solr",
////solrClientRef = "firstSolrClient",
////solrTemplateRef = "firstSolrTemplate")
//public class IndexingConfiguration {
//
//	@Autowired
//	private EntityManager entityManager;
//
//	@Bean
//	public FullTextEntityManager getFullTextEntityManager() throws InterruptedException {
//		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
//		fullTextEntityManager.createIndexer().startAndWait();
//		return fullTextEntityManager;
//	}
//	
////	@Value("${spring.data.solr.host}")
////    private String solrHost;
////	
////	 @Bean
////     public SolrClient solrClient() {
////         return new HttpSolrClient.Builder(solrHost).build();
////     }
//
////     @Bean
////     public SolrTemplate solrTemplate() {
////         return new SolrTemplate(this::solrClient);
////     }
//	
////	 @Bean
////	  public SolrClient solrClient() {
////	    EmbeddedSolrServerFactory factory = new EmbeddedSolrServerFactoryBean();
////	    return factory.getSolrClient();
////	  }
//
////	  @Bean
////	  public SolrOperations solrTemplate() {
////	    return new SolrTemplate(solrClient());
////	  }
//     
//	
//}