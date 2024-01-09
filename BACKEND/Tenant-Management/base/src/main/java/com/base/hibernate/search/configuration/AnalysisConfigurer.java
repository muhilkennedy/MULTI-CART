package com.base.hibernate.search.configuration;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * @author I339628
 * default configurer used for hibernate search.
 */
@Configuration
public class AnalysisConfigurer implements LuceneAnalysisConfigurer {

	@Override
	public void configure(LuceneAnalysisConfigurationContext context) {
		// TODO Auto-generated method stub
		context.analyzer("search").custom()
			.tokenizer(StandardTokenizerFactory.class)
			.tokenFilter(LowerCaseFilterFactory.class)
			.tokenFilter(SnowballPorterFilterFactory.class)
			.param("language", "English")
			.tokenFilter(EdgeNGramFilterFactory.class)
			//.tokenFilter(NGramFilterFactory.class)
			.param("minGramSize", "2")
			.param("maxGramSize", "15");
	}

}
