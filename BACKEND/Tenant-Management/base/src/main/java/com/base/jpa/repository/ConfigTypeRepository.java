package com.base.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.base.entity.ConfigType;
/**
 * @author Muhil
 */
@Repository
public interface ConfigTypeRepository extends JpaRepository<ConfigType, Long> {
	
	String findConfigByTypeQuery = "select conf from ConfigType conf where configtype=:configtype";

	@Query(findConfigByTypeQuery)
	List<ConfigType> findAllConfigByType(@Param("configtype") String configtype);
	
	String findByNameQuery = "select conf from ConfigType conf where name=:name and configtype=:configtype";

	@Query(findByNameQuery)
	List<ConfigType> findByConfig(@Param("name") String name, @Param("configtype") String configtype);

}
