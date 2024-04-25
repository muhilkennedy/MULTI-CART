package com.base.hibernate.search.configuration;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
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
		context.analyzer("search").custom()
			.tokenizer(StandardTokenizerFactory.class) // Splits words at punctuation characters, removing punctuations. 
			.tokenFilter(LowerCaseFilterFactory.class) // converts keywords to lower case
			.tokenFilter(SnowballPorterFilterFactory.class)
			.param("language", "English") // snowball is language analyser, matches english words
			//.tokenFilter(EdgeNGramFilterFactory.class) // startswith based search
			.tokenFilter(NGramFilterFactory.class) // contains based search
			.param("minGramSize", "2") 
			.param("maxGramSize", "15"); // muhil -> mu,um, il, mh, hu, muh, hil, etc
	}

}
