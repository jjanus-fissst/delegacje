package com.lbd.projectlbd.service;

import com.github.fge.jsonpatch.JsonPatch;
import com.lbd.projectlbd.api.model.CheckpointModelApi;
import com.lbd.projectlbd.apiresponse.StandardResponse;
import com.lbd.projectlbd.dto.CheckpointDto;
import com.lbd.projectlbd.dto.CommentToCheckpointDto;
import com.lbd.projectlbd.entity.Checkpoint;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CheckpointService {

    /**
     * Utilities */
    void add(Checkpoint checkpoint);
    void deleteCheckpoint(Long id);
    List<CheckpointModelApi> getCheckpoint(Long id);

    void patch(Long id, JsonPatch patch);
    void update(Long id,CheckpointDto checkpointDto);
    void addComment(Long id, CommentToCheckpointDto commentToCheckpointDto);

    CommentToCheckpointDto getOneComment(Long id);
    List<CommentToCheckpointDto> findAllComment(Long id);
}
