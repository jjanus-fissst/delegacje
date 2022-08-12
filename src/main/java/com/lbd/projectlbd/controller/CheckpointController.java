package com.lbd.projectlbd.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.lbd.projectlbd.api.CheckpointsApi;
import com.lbd.projectlbd.api.model.CheckpointModelApi;
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


@RestController
@AllArgsConstructor
public class CheckpointController implements CheckpointsApi {

    CheckpointService checkpointService;
    CheckpointMapper checkpointMapper;
    CheckpointRepository checkpointRepository;
    CommentToCheckpointRepository commentToCheckpointRepository;
    CommentToCheckpointMapper commentToCheckpointMapper;

//    @Override
//    public ResponseEntity getCheckpoint(Long id) {
//        return ResponseEntity.ok().body(checkpointService.getCheckpoint(id));
//    }

    @Override
    public ResponseEntity<Void> deleteCheckpoint(Long id) {
        checkpointService.deleteCheckpoint(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> update(Long id, CheckpointModelApi checkpointModelApi) {
       CheckpointDto checkpointDto= checkpointMapper.mapCheckpointModelApiToCheckpointDto(checkpointModelApi);
        checkpointService.update(id,checkpointDto);
        return ResponseEntity.ok().build();
    }


@PostMapping(value = "/add/{id}")
public void add(@PathVariable Long id, @RequestBody CommentToCheckpointDto commentToCheckpointDto){
        checkpointService.addComment(id,commentToCheckpointDto);
}


    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<StandardResponse> changeStatus(@PathVariable Long id, @RequestBody JsonPatch patch)  {
        checkpointService.patch(id, patch);
        return new StandardResponse(HttpStatus.OK, "Checkpoint patched").buildResponseEntity();
    }



//    @PostMapping("/api/add")
//    public void add(@RequestBody CommentToCheckpointDto commentToCheckpointDto){
//        Checkpoint checkpoint= checkpointRepository.findById(1L).orElseThrow(()->new RuntimeException("xdd"));
//        CommentToCheckpoint commentToCheckpoint=commentToCheckpointMapper.mapCommentToCheckpointDtoToCommentToCheckpoint(commentToCheckpointDto);
//        commentToCheckpoint.setCheckpoint(checkpoint);
//        commentToCheckpointRepository.save(commentToCheckpoint);
//
//    }




    //    @Override
//    public ResponseEntity<Void> changeStatus(Long id, CheckpointModelApi checkpointModelApi) {
//        checkpointService.patch(id, patch);
//        return CheckpointsApi.super.changeStatus(id, checkpointModelApi);
//    }

    //    @PutMapping("/{id}")
//    public ResponseEntity<StandardResponse> update(@PathVariable Long id, @RequestBody CheckpointDto checkpointDto){
//        checkpointService.update(id,checkpointDto);
//        return new StandardResponse(HttpStatus.OK,"Checkpoint updated").buildResponseEntity();
//    }
}
