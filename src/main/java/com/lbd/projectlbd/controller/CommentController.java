package com.lbd.projectlbd.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.lbd.projectlbd.api.CommentsApi;
import com.lbd.projectlbd.apiresponse.StandardResponse;
import com.lbd.projectlbd.dto.CommentDto;
import com.lbd.projectlbd.entity.Comment;
import com.lbd.projectlbd.mapper.CommentMapper;
import com.lbd.projectlbd.service.comment.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.lbd.projectlbd.api.CommentsApi;
import com.lbd.projectlbd.api.model.CommentV2ModelApi;
import com.lbd.projectlbd.api.model.CommentModelApi;

@RestController
@RequiredArgsConstructor
public class CommentController implements CommentsApi {

    private final CommentServiceImpl commentService;
    private final CommentMapper commentMapper;

    @Override
    public ResponseEntity<CommentModelApi> addComment(CommentModelApi commentModelApi){
        CommentDto commentDto= commentMapper.converntApiToDto(commentModelApi);
        commentService.add(commentDto);
        return new ResponseEntity<>(commentModelApi,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CommentV2ModelApi> addCommentV2(CommentV2ModelApi commentV2ModelApi){
        CommentDto commentDto=commentMapper.convertApiv2ToDtop(commentV2ModelApi);
        commentService.add(commentDto);
        return new ResponseEntity<>(commentV2ModelApi,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteComment(@PathVariable Long id){
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateComment(@PathVariable Long id, @RequestBody CommentModelApi commentModelApi) {
        CommentDto commentDto=commentMapper.converntApiToDto(commentModelApi);
        commentService.update(id,commentDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CommentModelApi>> getCommentById(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.getAllByUpComment(id)
                .stream().map(commentMapper::convertDtoToModelApi)
                .collect(Collectors.toList())
                ,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CommentModelApi>> getComments() {
        return new ResponseEntity<>(commentService.getAllComments()
                .stream().map(commentMapper::convertDtoToModelApi)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

//    @GetMapping("parent/{parentId}")
//    public ResponseEntity<List<CommentDto>> getCommentByParentId(@PathVariable Long parentId) {
//        return ResponseEntity.ok().body(commentService.getAllByUpComment(parentId));
//    }
//
//    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
//    public ResponseEntity<StandardResponse> patchComment(@PathVariable Long id, @RequestBody JsonPatch patch) {
//        commentService.patch(id, patch);
//        return new StandardResponse(HttpStatus.OK, "Comment patched").buildResponseEntity();
//    }
//
//    @GetMapping("delegation/{delegationId}")
//    public ResponseEntity<List<CommentDto>> getCommentByDelegationId(@PathVariable Long delegationId) {
//        return ResponseEntity.ok().body(commentService.getAllByDelegationId(delegationId));
//    }
//
//        @DeleteMapping(path = "/{id}")
//    public ResponseEntity<StandardResponse> deleteComment(@PathVariable Long id) {
//        commentService.delete(id);
//        return new StandardResponse(HttpStatus.OK, "Comment deleted").buildResponseEntity();
//    }
}