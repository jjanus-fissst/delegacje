package com.lbd.projectlbd.mapper;

import com.lbd.projectlbd.dto.CheckpointDto;
import com.lbd.projectlbd.dto.CommentToCheckpointDto;
import com.lbd.projectlbd.entity.Checkpoint;
import com.lbd.projectlbd.entity.CommentToCheckpoint;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentToCheckpointMapper {

    @Named("mapCommentToCheckpointDtoToCommentToCheckpoint")
   @Mapping(source = "comment", target = "comment")
    CommentToCheckpoint mapCommentToCheckpointDtoToCommentToCheckpoint(CommentToCheckpointDto source);
    CommentToCheckpointDto mapCommentToCheckpointToCommentToCheckpointDto(CommentToCheckpoint commentToCheckpoint);


}
