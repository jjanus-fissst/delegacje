package com.lbd.projectlbd.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.lbd.projectlbd.api.CheckpointsApi;
import com.lbd.projectlbd.api.model.CheckpointModelApi;
import com.lbd.projectlbd.api.model.CheckpointV2ModelApi;
import com.lbd.projectlbd.api.model.CommentToCheckpointModelApi;
import com.lbd.projectlbd.apiresponse.StandardResponse;
import com.lbd.projectlbd.dto.CheckpointDto;
import com.lbd.projectlbd.dto.CommentToCheckpointDto;
import com.lbd.projectlbd.mapper.CheckpointMapper;
import com.lbd.projectlbd.mapper.CommentToCheckpointMapper;
import com.lbd.projectlbd.repository.CheckpointRepository;
import com.lbd.projectlbd.repository.CommentToCheckpointRepository;
import com.lbd.projectlbd.service.CheckpointService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
public class CheckpointController implements CheckpointsApi {

    CheckpointService checkpointService;
    CheckpointMapper checkpointMapper;
    CheckpointRepository checkpointRepository;
    CommentToCheckpointRepository commentToCheckpointRepository;
    CommentToCheckpointMapper commentToCheckpointMapper;

    @Override
    public ResponseEntity<List<CheckpointV2ModelApi>> getCheckpointsV2(Long delegationId) {
        List<CheckpointDto> checkpointDtos = checkpointService.getCheckpoint(delegationId);
       return ResponseEntity.ok().body( checkpointDtos.stream().map(checkpointDto -> {
            CheckpointV2ModelApi checkpointV2ModelApi= checkpointMapper.mapDtoToModelApiV2(checkpointDto);
            checkpointV2ModelApi.setComment(commentToCheckpointMapper.mapDtoListToModelApiList(checkpointDto.getCommentToCheckpointDtos()));
return checkpointV2ModelApi;
        }).collect(Collectors.toList()));
    }



    @Override
    public ResponseEntity<CommentToCheckpointModelApi> getCheckpointComment(Long checkpointId) {
        CommentToCheckpointDto commentToCheckpointDto = checkpointService.getOneComment(checkpointId);
        return ResponseEntity.ok().body(commentToCheckpointMapper.mapDtoToModelApi(commentToCheckpointDto));
    }

    @Override
    public ResponseEntity<List<CommentToCheckpointModelApi>> getCheckpointComments(Long checkpointId) {
        List<CommentToCheckpointDto> commentToCheckpointDtoList = checkpointService.getAllComment(checkpointId);
        return ResponseEntity.ok().body(commentToCheckpointMapper.mapDtoListToModelApiList(commentToCheckpointDtoList));
    }

    @Override
    public ResponseEntity<Void> deleteCheckpoint(Long id) {
        checkpointService.deleteCheckpoint(id);
        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity<Void> updateV2(Long id, CheckpointV2ModelApi checkpointV2ModelApi) {
        CheckpointDto checkpointDto = checkpointMapper.mapModelApiV2ToDto(checkpointV2ModelApi);
        checkpointService.update(id, checkpointDto);
        return ResponseEntity.ok().build();
    }

    //Add comment to checkpoint
    @Override
    public ResponseEntity<Void> add(Long checkpointId, CommentToCheckpointModelApi commentToCheckpointModelApi) {
        CommentToCheckpointDto commentToCheckpointDto = commentToCheckpointMapper.mapModelApiToDTO(commentToCheckpointModelApi);
        checkpointService.addComment(checkpointId, commentToCheckpointDto);
        return ResponseEntity.ok().build();
    }


    @PatchMapping(value = "patch/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<StandardResponse> changeStatus(@PathVariable Long id, @RequestBody JsonPatch patch) {
        checkpointService.patch(id, patch);
        return new StandardResponse(HttpStatus.OK, "Checkpoint patched").buildResponseEntity();
     }


}


