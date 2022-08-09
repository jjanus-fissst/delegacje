package com.lbd.projectlbd.repository;

import com.lbd.projectlbd.entity.Checkpoint;
import com.lbd.projectlbd.entity.Delegation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckpointRepository extends PagingAndSortingRepository<Checkpoint, Long> {


}
