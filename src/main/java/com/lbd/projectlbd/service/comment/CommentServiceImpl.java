package com.lbd.projectlbd.service.comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.lbd.projectlbd.dto.CommentDto;
import com.lbd.projectlbd.entity.Comment;
import com.lbd.projectlbd.mapper.CommentMapper;
import com.lbd.projectlbd.repository.CommentRepository;
import com.lbd.projectlbd.repository.DelegationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final DelegationRepository delegationRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto findById(Long id) {
        return commentMapper
                .convertCommentToDto(commentRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Comment with id=" + id + " not found!")));
    }

    @Override
    public List<CommentDto> getAllByDelegationId(Long delegationId) {
        if (!delegationRepository.existsById(delegationId))
            throw new EntityNotFoundException("Delegation with id=" + delegationId + " not found!");
        return commentRepository.getAllByDelegationId(delegationId)
                .stream()
                .map(commentMapper::convertCommentToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = (List<Comment>) commentRepository.findAll();
        return comments.stream()
                .map(commentMapper::convertCommentToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getAllByDate(LocalDateTime date) {
        return commentRepository.getAllByDate(Timestamp.valueOf(date))
                .stream()
                .map(commentMapper::convertCommentToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getAllByUpComment(Long commentId) {
        if (!commentRepository.existsById(commentId))
            throw new EntityNotFoundException("Comment with id=" + commentId + " not found!");
        return commentRepository.getAllByUpComment(commentId)
                .stream()
                .map(commentMapper::convertCommentToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void add(CommentDto commentDto) {
        if (commentDto.getDelegationId() == null)
            throw new IllegalArgumentException("Delegation id cannot be null!");
        if (!delegationRepository.existsById(commentDto.getDelegationId()))
            throw new EntityNotFoundException("Delegation with id=" + commentDto.getDelegationId() + " not found!");
        if (commentDto.getParentId() != null && !commentRepository.existsById(commentDto.getParentId()))
            throw new EntityNotFoundException("Comment with id=" + commentDto.getParentId() + " not found!");
        if(commentDto.getTitle() == null)
            commentDto.setTitle(String.format("Comment by %s", commentDto.getAuthor()));
        Comment comment = commentMapper.convertDtoToComment(commentDto);
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void update(Long id, CommentDto commentDto) {
        if (commentDto.getDelegationId() != null && !delegationRepository.existsById(commentDto.getDelegationId()))
            throw new EntityNotFoundException("Delegation with id=" + commentDto.getDelegationId() + " not found!");
        if (commentDto.getParentId() != null && !commentRepository.existsById(commentDto.getParentId()))
            throw new EntityNotFoundException("Comment with id=" + commentDto.getParentId() + " not found!");
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment with id=" + id + " not found!"));
        commentRepository.save(commentMapper.updateComment(commentDto, comment));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment with id=" + id + " not found!"));
        if (!comment.getCommentList().isEmpty())
            commentRepository.deleteAll(comment.getCommentList());
        commentRepository.delete(comment);
    }

    @Override
    public void patch(Long id, String body) {
        final ObjectMapper mapper = new ObjectMapper();
        final InputStream in = new ByteArrayInputStream(body.getBytes());
        try {
            final JsonPatch patch = mapper.readValue(in, JsonPatch.class);
            CommentDto comment = commentMapper.convertCommentToDto(commentRepository
                    .findById(id).orElseThrow(() -> new EntityNotFoundException("Comment with id=" + id + " not found!")));
            CommentDto commentPatched = applyPatchToComment(patch, comment);
            update(id, commentPatched);
        } catch (JsonPatchException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    private CommentDto applyPatchToComment(JsonPatch patch, CommentDto targetComment) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        JsonNode patched = patch.apply(objectMapper.convertValue(targetComment, JsonNode.class));
        return objectMapper.treeToValue(patched, CommentDto.class);
    }
}