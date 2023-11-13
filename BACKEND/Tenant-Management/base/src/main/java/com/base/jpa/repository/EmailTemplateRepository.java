package com.base.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.base.entity.EmailTemplate;

/**
 * @author Muhil
 */
@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {

	String findEmailTemplateQuery = "select template from EmailTemplate template where name=:name";

	@Query(findEmailTemplateQuery)
	EmailTemplate findEmailTemplate(@Param("name") String name);

}
