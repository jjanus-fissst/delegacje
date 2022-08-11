package com.lbd.projectlbd.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import com.lbd.projectlbd.dto.CheckpointDto;
import com.lbd.projectlbd.entity.Checkpoint;
import com.lbd.projectlbd.entity.Delegation;
import com.lbd.projectlbd.mapper.CheckpointMapper;
import com.lbd.projectlbd.repository.CheckpointRepository;
import com.lbd.projectlbd.repository.DelegationRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CheckpointServiceImpl implements CheckpointService {

    CheckpointRepository checkpointRepository;
    DelegationRepository delegationRepository;
    CheckpointMapper checkpointMapper;




    /**
     * Utilities */
    @Override public void add(Checkpoint checkpoint) {
        checkpointRepository.save(checkpoint);

    }


    @Override
    public List<CheckpointDto> getCheckpoint(Long id){
        Optional<Delegation> delegation= delegationRepository.findById(id);

        return  delegation.map(delegation1 -> delegation1.getCheckpointSet().stream().map(checkpoint ->checkpointMapper.mapCheckpointToCheckpointDto(checkpoint) ).collect(Collectors.toList())
                )
                .orElseThrow(()->new EntityNotFoundException("Delegation not found!"));

    }

    @Override
    public void deleteCheckpoint(Long id){
         Checkpoint checkpoint=checkpointRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Checkpoint with id=" + id + " not found!"));

        checkpoint.getDelegation().getCheckpointSet().remove(checkpoint);
        checkpointRepository.deleteById(id);


    }

@Override
    public void update(Long id,CheckpointDto checkpointDto){
        Checkpoint checkpoint=checkpointRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Checkpoint with id=" + id + " not found!"));
        checkpointRepository.save(checkpointMapper.updateCheckpoint(checkpoint,checkpointDto));
    }

    @Override
    public void patch(Long id, JsonPatch patch) {



        try {
            Checkpoint checkpoint = checkpointRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Checkpoint with id=" + id + " not found!"));
            Checkpoint checkpointPatched = applyPatchToCheckpoint(patch, checkpoint);
            update(id,checkpointMapper.mapCheckpointToCheckpointDto (checkpointPatched) );
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private Checkpoint applyPatchToCheckpoint(JsonPatch patch, Checkpoint targetCheckpoint) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CheckpointDto checkpointDto = checkpointMapper.mapCheckpointToCheckpointDto(targetCheckpoint);
        JsonNode patched = patch.apply(objectMapper.convertValue(checkpointDto, JsonNode.class));
        return objectMapper.treeToValue(patched, Checkpoint.class);
    }


}
