package com.lbd.projectlbd.mapper;

import com.lbd.projectlbd.api.model.CheckpointModelApi;
import com.lbd.projectlbd.dto.CheckpointDto;
import com.lbd.projectlbd.dto.DelegationDto;
import com.lbd.projectlbd.entity.Checkpoint;
import com.lbd.projectlbd.entity.Delegation;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CheckpointMapper {
    @Named("updateCheckpoint")
    Checkpoint updateCheckpoint(@MappingTarget Checkpoint checkpoint, CheckpointDto checkpointDto);
    @Named("mapCheckpointToCheckpointDto")
    @Mapping(source = "delegation.id",target = "delegationId")
    CheckpointDto mapCheckpointToCheckpointDto(Checkpoint source);

    @Named("mapCheckpointDtoToCheckpoint")
    Checkpoint mapCheckpointDtoToCheckpoint(CheckpointDto source);
    @Named("mapCheckpointDtoToCheckpointModelApi")
    CheckpointModelApi mapCheckpointDtoToCheckpointModelApi(CheckpointDto source);
    @Named("mapCheckpointModelApiToCheckpointDto")
    CheckpointDto mapCheckpointModelApiToCheckpointDto(CheckpointModelApi source);


}
