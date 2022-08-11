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
    public ResponseEntity<CommentModelApi> addComment(CommentModelApi commentModelApi) {
        CommentDto commentDto = commentMapper.convertApiToDto(commentModelApi);
        commentService.add(commentDto);
        return new ResponseEntity<>(commentModelApi, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CommentV2ModelApi> addCommentV2(CommentV2ModelApi commentV2ModelApi) {
        CommentDto commentDto = commentMapper.convertApiV2ToDto(commentV2ModelApi);
        commentService.add(commentDto);
        return new ResponseEntity<>(commentV2ModelApi, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteCommentV2(@PathVariable Long id) {
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CommentModelApi>> getCommentByDelegationId(@PathVariable Long delegationId) {
        return new ResponseEntity<>(commentService.getAllByDelegationId(delegationId)
                .stream()
                .map(commentMapper::convertDtoToModelApi)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CommentV2ModelApi>> getCommentByDelegationIdV2(@PathVariable Long delegationId) {
        return new ResponseEntity<>(commentService.getAllByDelegationId(delegationId)
                .stream()
                .map(commentMapper::convertDtoToApiV2)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommentModelApi> getCommentById(@PathVariable Long id) {
        return new ResponseEntity<>(commentMapper.convertDtoToModelApi(commentService.findById(id)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommentV2ModelApi> getCommentByIdV2(@PathVariable Long id) {
        return new ResponseEntity<>(commentMapper.convertDtoToApiV2(commentService.findById(id)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CommentModelApi>> getCommentByParentId(@PathVariable Long parentId) {
        return new ResponseEntity<>(commentService.getAllByUpComment(parentId)
                .stream().map(commentMapper::convertDtoToModelApi)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CommentV2ModelApi>> getCommentByParentIdV2(@PathVariable Long parentId) {
        return new ResponseEntity<>(commentService.getAllByUpComment(parentId)
                .stream().map(commentMapper::convertDtoToApiV2)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CommentModelApi>> getComments() {
        return new ResponseEntity<>(commentService.getAllComments()
                .stream().map(commentMapper::convertDtoToModelApi)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CommentV2ModelApi>> getCommentsV2() {
        return new ResponseEntity<>(commentService.getAllComments()
                .stream().map(commentMapper::convertDtoToApiV2)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateComment(@PathVariable Long id, @RequestBody CommentModelApi commentModelApi) {
        CommentDto commentDto = commentMapper.convertApiToDto(commentModelApi);
        commentService.update(id, commentDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateCommentV2(@PathVariable Long id, @RequestBody CommentV2ModelApi commentModelApi) {
        CommentDto commentDto = commentMapper.convertApiV2ToDto(commentModelApi);
        commentService.update(id, commentDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
//    public ResponseEntity<StandardResponse> patchComment(@PathVariable Long id, @RequestBody JsonPatch patch) {
//        commentService.patch(id, patch);
//        return new StandardResponse(HttpStatus.OK, "Comment patched").buildResponseEntity();
//    }

}