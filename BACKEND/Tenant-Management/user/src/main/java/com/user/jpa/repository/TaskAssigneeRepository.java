package com.user.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.entity.TaskAssignee;

/**
 * @author Muhil
 *
 */
@Repository
public interface TaskAssigneeRepository extends JpaRepository<TaskAssignee, Long> {
	
	String getPendingTasksCountQuery = "select count(1) from TaskAssignee tsk where tsk.assignee.rootid=:assigneeId and tsk.status=:status";

	@Query(getPendingTasksCountQuery)
	Integer getPendingTasksCount(@Param("assigneeId") Long assigneeId, @Param("status") String status);

}
