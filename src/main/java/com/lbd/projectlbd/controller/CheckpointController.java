package com.lbd.projectlbd.controller;

import com.lbd.projectlbd.apiresponse.StandardResponse;
import com.lbd.projectlbd.dto.CheckpointDto;
import com.lbd.projectlbd.entity.Checkpoint;
import com.lbd.projectlbd.service.CheckpointService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/checkpoint")
public class CheckpointController {

    CheckpointService checkpointService;

    public CheckpointController(CheckpointService checkpointService) {
        this.checkpointService = checkpointService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<List<CheckpointDto>>getCheckpoint(@PathVariable long id){
        return ResponseEntity.ok().body(checkpointService.getCheckpoint(id));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<StandardResponse> deleteCheckpoint(@PathVariable long id){
        checkpointService.deleteCheckpoint(id);
        return new StandardResponse(HttpStatus.OK,"Checkpoint deleted").buildResponseEntity();
    }




}
