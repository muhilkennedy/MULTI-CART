package com.base.reactive.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.base.entity.QuartzJobInfo;

@Repository
public interface QuartzReactiveRepository extends R2dbcRepository<QuartzJobInfo, Long> {

}
