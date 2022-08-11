package com.lbd.projectlbd.mapper;

import com.lbd.projectlbd.dto.CommentDto;
import com.lbd.projectlbd.entity.Comment;
import com.lbd.projectlbd.entity.Delegation;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mapping(source = "delegation.id", target = "delegationId")
    @Mapping(source = "comment.id", target = "parentId")
    CommentDto convertCommentToDto(Comment comment);

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(source = "delegationId", target = "delegation")
    @Mapping(source = "parentId", target = "comment", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    Comment convertDtoToComment(CommentDto commentDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "delegationId", target = "delegation")
    @Mapping(source = "parentId", target = "comment")
    Comment updateComment(CommentDto commentDto, @MappingTarget Comment comment);

    @Mapping(target = "id")
    Delegation mapDelegationById(Long id);
    @Mapping(target = "id")
    Comment mapCommentById(Long id);
}
