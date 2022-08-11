package com.lbd.projectlbd.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.lbd.projectlbd.apiresponse.StandardResponse;
import com.lbd.projectlbd.dto.CheckpointDto;
import com.lbd.projectlbd.entity.Checkpoint;
import com.lbd.projectlbd.service.CheckpointService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/checkpoint")
@AllArgsConstructor
public class CheckpointController {

    CheckpointService checkpointService;



    @GetMapping(value = "{id}")
    public ResponseEntity<List<CheckpointDto>>getCheckpoint(@PathVariable long id){
        return ResponseEntity.ok().body(checkpointService.getCheckpoint(id));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<StandardResponse> deleteCheckpoint(@PathVariable long id){
        checkpointService.deleteCheckpoint(id);
        return new StandardResponse(HttpStatus.OK,"Checkpoint deleted").buildResponseEntity();
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<StandardResponse> changeStatus(@PathVariable Long id, @RequestBody JsonPatch patch)  {
        checkpointService.patch(id, patch);
        return new StandardResponse(HttpStatus.OK, "Checkpoint patched").buildResponseEntity();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardResponse> update(@PathVariable Long id, @RequestBody CheckpointDto checkpointDto){
        checkpointService.update(id,checkpointDto);
        return new StandardResponse(HttpStatus.OK,"Checkpoint updated").buildResponseEntity();
    }


}
