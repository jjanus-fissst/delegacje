package com.lbd.projectlbd.mapper;

import com.lbd.projectlbd.dto.CommentDto;
import com.lbd.projectlbd.entity.Comment;
import com.lbd.projectlbd.entity.Delegation;
import org.mapstruct.*;
import com.lbd.projectlbd.api.model.CommentModelApi;
import com.lbd.projectlbd.api.model.CommentV2ModelApi;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

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


    @Mapping(source = "date", target = "date", qualifiedByName = "offsetDateToLocalDate")
    CommentDto converntApiToDto(CommentModelApi commentModelApi);

    @Mapping(source = "date", target = "date", qualifiedByName = "localDateToOffsetDate")
    CommentModelApi convertDtoToModelApi(CommentDto commentDto);

    @Mapping(source = "date", target = "date", qualifiedByName = "offsetDateToLocalDate")
    CommentDto convertApiv2ToDtop(CommentV2ModelApi commentV2ModelApi);

    @Mapping(source = "date", target = "date", qualifiedByName = "localDateToOffsetDate")
    CommentV2ModelApi convertDtoToApiV2(CommentDto commentDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "delegationId", target = "delegation")
    @Mapping(source = "parentId", target = "comment")
    Comment updateComment(CommentDto commentDto, @MappingTarget Comment comment);

    @Mapping(target = "id")
    Delegation mapDelegationById(Long id);
    @Mapping(target = "id")
    Comment mapCommentById(Long id);

    @Named("offsetDateToLocalDate")
    default LocalDateTime offsetDateToLocalDate(OffsetDateTime date) {
        return date.toLocalDateTime();
    }

    //TODO: returns something weird but works
    @Named("localDateToOffsetDate")
    default OffsetDateTime localDateToOffsetDate(LocalDateTime date) {
        return date.atOffset(OffsetDateTime.now().getOffset());
    }
}
