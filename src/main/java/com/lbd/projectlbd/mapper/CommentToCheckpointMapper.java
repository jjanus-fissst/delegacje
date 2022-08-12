package com.lbd.projectlbd.mapper;

import com.lbd.projectlbd.dto.CheckpointDto;
import com.lbd.projectlbd.dto.CommentToCheckpointDto;
import com.lbd.projectlbd.dto.DelegationDto;
import com.lbd.projectlbd.entity.Checkpoint;
import com.lbd.projectlbd.entity.CommentToCheckpoint;
import com.lbd.projectlbd.entity.Delegation;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentToCheckpointMapper {

    @Named("mapCommentToCheckpointDtoToCommentToCheckpoint")
    @Mapping(source = "date",target = "date")

    CommentToCheckpoint mapCommentToCheckpointDtoToCommentToCheckpoint(CommentToCheckpointDto source);
    @Named("mapCommentToCheckpointToCommentToCheckpointDto")
    @Mapping(source = "checkpoint.id",target = "checkpointId")
    CommentToCheckpointDto mapCommentToCheckpointToCommentToCheckpointDto(CommentToCheckpoint commentToCheckpoint);
    @IterableMapping(qualifiedByName = "mapCommentToCheckpointToCommentToCheckpointDto")
    @Named("mapCommentToCheckpointListToCommentToCheckpointDtoList")
    List<CommentToCheckpointDto> mapCommentToCheckpointListToCommentToCheckpointDtoList(List<CommentToCheckpoint> source);

}
