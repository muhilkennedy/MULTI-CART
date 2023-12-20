package com.user.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.entity.Task;

/**
 * @author Muhil
 *
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	String findByTypeQuery = "select tsk from Task tsk where tasktype=:taskType";

	@Query(findByTypeQuery)
	Page<Task> findByType(@Param("taskType") String taskType, Pageable pageable);

	String findByStatusQuery = "select tsk from Task tsk where status=:status";

	@Query(findByStatusQuery)
	Page<Task> findByStatus(@Param("status") String status, Pageable pageable);

	String findAllUserTaskQuery = "select tsk from Task tsk inner join TaskAssignee as ta on ta.task.rootid = tsk.rootid where ta.assignee.rootid=:asigneeId or tsk.owner.rootid=:asigneeId";

	@Query(findAllUserTaskQuery)
	Page<Task> findAllTasksUserPartOfTask(@Param("asigneeId") Long asigneeId, Pageable pageable);
	
	String findAllUserTaskByStatusQuery = "select tsk from Task tsk inner join TaskAssignee as ta on ta.task.rootid = tsk.rootid where (ta.assignee.rootid=:asigneeId or tsk.owner.rootid=:asigneeId) and tsk.status=:status";

	@Query(findAllUserTaskByStatusQuery)
	Page<Task> findAllUserTaskByStatus(@Param("status") String status, @Param("asigneeId") Long asigneeId, Pageable pageable);
	
	String findAllBroadCastTaskQuery = "select tsk from Task tsk where tsk.owner.rootid=:ownerId and tsk.broadcast=true";

	@Query(findAllBroadCastTaskQuery)
	Page<Task> findAllBroadCastTask(@Param("ownerId") Long ownerId, Pageable pageable);

}
