package com.lbd.projectlbd.repository;

import com.lbd.projectlbd.entity.Delegation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DelegationRepository extends PagingAndSortingRepository<Delegation, Long> {

    List<Delegation> findAll();
    void deleteById(Long id);
}
