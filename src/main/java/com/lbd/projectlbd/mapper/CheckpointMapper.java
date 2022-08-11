package com.lbd.projectlbd.mapper;

import com.lbd.projectlbd.dto.CheckpointDto;
import com.lbd.projectlbd.entity.Checkpoint;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CheckpointMapper {
    @Named("updateCheckpoint")
    Checkpoint updateCheckpoint(@MappingTarget Checkpoint checkpoint, CheckpointDto checkpointDto);
    @Named("mapCheckpointToCheckpointDto")
    CheckpointDto mapCheckpointToCheckpointDto(Checkpoint source);

    @Named("mapCheckpointDtoToCheckpoint")
    Checkpoint mapCheckpointDtoToCheckpoint(CheckpointDto source);
}
