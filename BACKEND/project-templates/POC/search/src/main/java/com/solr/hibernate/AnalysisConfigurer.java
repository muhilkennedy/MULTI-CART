package com.solr.hibernate;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnalysisConfigurer implements LuceneAnalysisConfigurer {

	@Override
	public void configure(LuceneAnalysisConfigurationContext context) {
		// TODO Auto-generated method stub
		context.analyzer("english").custom()
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
