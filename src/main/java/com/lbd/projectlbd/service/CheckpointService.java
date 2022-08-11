package com.lbd.projectlbd.service;

import com.github.fge.jsonpatch.JsonPatch;
import com.lbd.projectlbd.apiresponse.StandardResponse;
import com.lbd.projectlbd.dto.CheckpointDto;
import com.lbd.projectlbd.entity.Checkpoint;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CheckpointService {

    /**
     * Utilities */
    void add(Checkpoint checkpoint);
    void deleteCheckpoint(Long id);
    List<CheckpointDto> getCheckpoint(Long id);

    void patch(Long id, JsonPatch patch);
    void update(Long id,CheckpointDto checkpointDto);


}
