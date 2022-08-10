package com.lbd.projectlbd.service;

import com.lbd.projectlbd.apiresponse.StandardResponse;
import com.lbd.projectlbd.dto.CheckpointDto;
import com.lbd.projectlbd.entity.Checkpoint;
import com.lbd.projectlbd.entity.Delegation;
import com.lbd.projectlbd.mapper.CheckpointMapper;
import com.lbd.projectlbd.repository.CheckpointRepository;
import com.lbd.projectlbd.repository.DelegationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CheckpointServiceImpl implements CheckpointService {

    CheckpointRepository checkpointRepository;
    DelegationRepository delegationRepository;



    public CheckpointServiceImpl(CheckpointRepository checkpointRepository, DelegationRepository delegationRepository) {
        this.checkpointRepository = checkpointRepository;
        this.delegationRepository = delegationRepository;
    }

    /**
     * Utilities */
    @Override public void add(Checkpoint checkpoint) {
        checkpointRepository.save(checkpoint);

    }


    @Override
    public List<CheckpointDto> getCheckpoint(Long id){
        Optional<Delegation> delegation= delegationRepository.findById(id);

        return  delegation.map(delegation1 -> delegation1.getCheckpointSet().stream().map(CheckpointMapper::convertEntityToDto).collect(Collectors.toList())
                )
                .orElseThrow(()->new EntityNotFoundException("Delegation not found!"));

    }

    @Override
    public void deleteCheckpoint(Long id){
        Optional<Checkpoint> checkpointOptional=checkpointRepository.findById(id);
        checkpointOptional.ifPresent(optional->optional.getDelegation().getCheckpointSet().remove(checkpointOptional.get()));

        checkpointRepository.deleteById(id);


    }
    @Override
    public ResponseEntity<StandardResponse> changeStatus(Checkpoint checkpoint, boolean status){
        checkpoint.setIsChecked(status);
        return new StandardResponse(HttpStatus.OK, "Status changed").buildResponseEntity();
    }


}
