package com.base.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.base.entity.QuartzJobInfo;

/**
 * @author Muhil
 */
@Repository
public interface QuartzJobRepository extends JpaRepository<QuartzJobInfo, Long> {

	String findJobQuery = "select job from QuartzJobInfo job where jobgroup=:groupName and jobname=:jobName";

	@Query(findJobQuery)
	QuartzJobInfo findJob(@Param("groupName") String groupName, @Param("jobName") String jobName);
	
	String findAllGroupNameQuery = "select distinct(jobgroup) from QuartzJobInfo";

	@Query(findAllGroupNameQuery)
	List<String> findAllGroupName();
	
	String findAllJobForGroupQuery = "select job from QuartzJobInfo job where jobgroup=:jobgroup";

	@Query(findAllJobForGroupQuery)
	Page<QuartzJobInfo> findAllJobForGroup(@Param("jobgroup") String jobgroup, Pageable pageable);

	String findAllJobForTenantQuery = "select job from QuartzJobInfo job where tenantid=:tenantId";

	@Query(findAllJobForTenantQuery)
	List<QuartzJobInfo> findAllJobsForTenant(@Param("tenantId") Long tenantId);

	@Query(findAllJobForTenantQuery)
	Page<QuartzJobInfo> findAllJobsForTenant(@Param("tenantId") Long tenantId, Pageable pageable);

}
