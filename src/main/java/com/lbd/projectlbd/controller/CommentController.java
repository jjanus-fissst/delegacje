package com.lbd.projectlbd.controller;

import com.lbd.projectlbd.apiresponse.StandardResponse;
import com.lbd.projectlbd.dto.CommentDto;
import com.lbd.projectlbd.service.comment.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping
    public ResponseEntity<StandardResponse> addComment(@RequestBody CommentDto comment) {
        commentService.add(comment);
        return new StandardResponse(HttpStatus.OK, "Comment added").buildResponseEntity();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<StandardResponse> deleteComment(@PathVariable Long id) {
        commentService.delete(id);
        return new StandardResponse(HttpStatus.OK, "Comment deleted").buildResponseEntity();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<StandardResponse> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        commentService.update(id, commentDto);
        return new StandardResponse(HttpStatus.OK, "Comment updated").buildResponseEntity();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok().body(commentService.findById(id));
    }

    @GetMapping("delegation/{delegationId}")
    public ResponseEntity<List<CommentDto>> getCommentByDelegationId(@PathVariable Long delegationId) {
        return ResponseEntity.ok().body(commentService.getAllByDelegationId(delegationId));
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getComments() {
        return ResponseEntity.ok().body(commentService.getAllComments());
    }

    @GetMapping("parent/{parentId}")
    public ResponseEntity<List<CommentDto>> getCommentByParentId(@PathVariable Long parentId) {
        return ResponseEntity.ok().body(commentService.getAllByUpComment(parentId));
    }
}