package com.lbd.projectlbd.mapper;

import com.lbd.projectlbd.api.model.CommentToCheckpointModelApi;
import com.lbd.projectlbd.dto.CommentToCheckpointDto;
import com.lbd.projectlbd.entity.CommentToCheckpoint;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentToCheckpointMapper {

    @Named("mapDtoToEntity")
    @Mapping(source = "date",target = "date")
    CommentToCheckpoint mapDtoToEntity(CommentToCheckpointDto source);

    @Named("mapEntityToDto")
    @Mapping(source = "checkpoint.id",target = "checkpointId")
    CommentToCheckpointDto mapEntityToDto(CommentToCheckpoint commentToCheckpoint);

    @Named("mapDtoToModelApi")
    @Mapping(source = "checkpointId",target = "checkpointId")
    CommentToCheckpointModelApi mapDtoToModelApi(CommentToCheckpointDto source);

    @Named("mapModelApiToDTO")
    @Mapping(source = "checkpointId",target = "checkpointId")
    CommentToCheckpointDto mapModelApiToDTO(CommentToCheckpointModelApi source);

    @IterableMapping(qualifiedByName = "mapDtoToModelApi")
    @Named("mapDtoListToModelApiList")
    List<CommentToCheckpointModelApi> mapDtoListToModelApiList(List<CommentToCheckpointDto > source);

        @IterableMapping(qualifiedByName = "mapEntityToDto")
    @Named("mapEntityListToDtoList")
    List<CommentToCheckpointDto> mapEntityListToDtoList(List<CommentToCheckpoint> source);
}
