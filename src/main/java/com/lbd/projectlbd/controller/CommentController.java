package com.lbd.projectlbd.controller;


import com.lbd.projectlbd.apiresponse.StandardResponse;
import com.lbd.projectlbd.dto.CommentDto;
import com.lbd.projectlbd.service.comment.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl service;

    @PostMapping
    public ResponseEntity<StandardResponse> addComment(@RequestBody CommentDto comment) {
        service.add(comment);
        return new StandardResponse(HttpStatus.OK, "Comment added").buildResponseEntity();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<StandardResponse> deleteComment(@PathVariable Long id) {
        service.delete(id);
        return new StandardResponse(HttpStatus.OK, "Comment deleted").buildResponseEntity();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<StandardResponse> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        service.update(id, commentDto);
        return new StandardResponse(HttpStatus.OK, "Comment updated").buildResponseEntity();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping("delegation/{delegationId}")
    public ResponseEntity<List<CommentDto>> getCommentByDelegationId(@PathVariable Long delegationId) {
        return ResponseEntity.ok().body(service.getAllByDelegationId(delegationId));
    }
}