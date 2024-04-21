package com.base.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.base.entity.FileStore;

/**
 * @author Muhil
 *
 */
@Repository
public interface FileStoreRepository extends JpaRepository<FileStore, Long>  {

	String findAllClientFilesQuery = "select fs from FileStore fs where fs.clientfile = true and createdby= :ownerId";

	@Query(findAllClientFilesQuery)
	Page<FileStore> findAllClientFiles(@Param("ownerId") Long ownerId, Pageable pageable);
	
	String findTotalFilesQuery = "select count(1) from FileStore fs where fs.clientfile = true and createdby= :ownerId";

	@Query(findTotalFilesQuery)
	Integer findTotalFiles(@Param("ownerId") Long ownerId);
	
	String findTotalFileSizeQuery = "select sum(fs.size) from FileStore fs where fs.clientfile = true and createdby= :ownerId";

	@Query(findTotalFileSizeQuery)
	Long findTotalFileSize(@Param("ownerId") Long ownerId);

}
