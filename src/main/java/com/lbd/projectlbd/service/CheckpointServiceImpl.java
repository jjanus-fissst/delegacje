package com.lbd.projectlbd.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import com.lbd.projectlbd.dto.CheckpointDto;
import com.lbd.projectlbd.dto.CommentToCheckpointDto;
import com.lbd.projectlbd.entity.Checkpoint;
import com.lbd.projectlbd.entity.CommentToCheckpoint;
import com.lbd.projectlbd.entity.Delegation;
import com.lbd.projectlbd.mapper.CheckpointMapper;
import com.lbd.projectlbd.mapper.CommentToCheckpointMapper;
import com.lbd.projectlbd.repository.CheckpointRepository;
import com.lbd.projectlbd.repository.CommentToCheckpointRepository;
import com.lbd.projectlbd.repository.DelegationRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CheckpointServiceImpl implements CheckpointService {

    CheckpointRepository checkpointRepository;
    DelegationRepository delegationRepository;
    CheckpointMapper checkpointMapper;

    CommentToCheckpointMapper commentToCheckpointMapper;

    CommentToCheckpointRepository commentToCheckpointRepository;


    /**
     * Utilities */
    @Override public void add(Checkpoint checkpoint) {
        checkpointRepository.save(checkpoint);
    }


    @Override
    public List<CheckpointDto> getCheckpoint(Long delegationId){
        Optional<Delegation> delegation= delegationRepository.findById(delegationId);

        return  delegation.map(delegation1 -> delegation1.getCheckpointSet().
                        stream().map(checkpoint ->{
                            CheckpointDto checkpointDto= checkpointMapper.mapEntityToDto(checkpoint);
                           checkpointDto.setCommentToCheckpointDtos(Arrays.asList(getOneComment(checkpoint.getId())));
                            return checkpointDto;
                        }).collect(Collectors.toList())
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
            CheckpointDto checkpointPatched = applyPatchToCheckpoint(patch, checkpoint);
            update(id,checkpointPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private CheckpointDto applyPatchToCheckpoint(JsonPatch patch, Checkpoint targetCheckpoint) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CheckpointDto checkpointDto = checkpointMapper.mapEntityToDto(targetCheckpoint);
        checkpointDto.setCommentToCheckpointDtos(commentToCheckpointMapper.mapEntityListToDtoList(targetCheckpoint.getCommentToCheckpointList()));
        JsonNode patched = patch.apply(objectMapper.convertValue(checkpointDto, JsonNode.class));
        return objectMapper.treeToValue(patched, CheckpointDto.class);
    }
    @Override
    public void addComment(Long id, CommentToCheckpointDto commentToCheckpointDto){
        Checkpoint checkpoint = checkpointRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Checkpoint with id=" + id + " not found!"));
        commentToCheckpointDto.setDate(new Timestamp(System.currentTimeMillis()));
        CommentToCheckpoint commentToCheckpoint = commentToCheckpointMapper.mapDtoToEntity(commentToCheckpointDto);

        commentToCheckpoint.setCheckpoint(checkpoint);
        commentToCheckpointRepository.save(commentToCheckpoint);
    }

    @Override
    public CommentToCheckpointDto getOneComment(Long id){
        Checkpoint checkpoint = checkpointRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Checkpoint with id=" + id + " not found!"));
        if(checkpoint.getCommentToCheckpointList().size()<=0){
            return null;
        }
       checkpoint.getCommentToCheckpointList().sort(Comparator.comparing(CommentToCheckpoint::getDate));
       CommentToCheckpoint commentToCheckpoint= checkpoint.getCommentToCheckpointList().get(checkpoint.getCommentToCheckpointList().size()-1);
       return commentToCheckpointMapper.mapEntityToDto(commentToCheckpoint);

    }
    @Override
    public List<CommentToCheckpointDto> getAllComment(Long id){
        Checkpoint checkpoint = checkpointRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Checkpoint with id=" + id + " not found!"));
      return checkpoint.getCommentToCheckpointList().stream().map(commentToCheckpoint -> commentToCheckpointMapper.mapEntityToDto(commentToCheckpoint)).collect(Collectors.toList());

    }


    //    public List<CheckpointDto> getCheckpoint(Long id){
//        Optional<Delegation> delegation= delegationRepository.findById(id);
//
//        return  delegation.map(delegation1 -> delegation1.getCheckpointSet().
//                        stream().map(checkpoint ->{
//                            CheckpointDto checkpointDto= checkpointMapper.mapCheckpointToCheckpointDto(checkpoint);
//                            checkpointDto.setCommentToCheckpointDtos(commentToCheckpointMapper.mapCommentToCheckpointListToCommentToCheckpointDtoList(checkpoint.getCommentToCheckpointList()));
//                            return checkpointDto;
//                        }).collect(Collectors.toList())
//                )
//                .orElseThrow(()->new EntityNotFoundException("Delegation not found!"));
//
//    }




}
