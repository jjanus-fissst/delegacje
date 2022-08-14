package com.lbd.projectlbd.mapper;

import com.lbd.projectlbd.api.model.CheckpointModelApi;
import com.lbd.projectlbd.api.model.CheckpointV2ModelApi;
import com.lbd.projectlbd.dto.CheckpointDto;
import com.lbd.projectlbd.entity.Checkpoint;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CheckpointMapper {
    @Named("updateCheckpoint")
    Checkpoint updateCheckpoint(@MappingTarget Checkpoint checkpoint, CheckpointDto checkpointDto);
    @Named("mapEntityToDto")
    @Mapping(source = "delegation.id",target = "delegationId")
    CheckpointDto mapEntityToDto(Checkpoint source);

    @Named("mapModelApiToDto")
    CheckpointDto mapModelApiToDto(CheckpointModelApi source);
    @Named("mapDtoToModelApiV2")
    CheckpointV2ModelApi mapDtoToModelApiV2(CheckpointDto source);
    @Named("mapModelApiV2ToDto")
    CheckpointDto mapModelApiV2ToDto(CheckpointV2ModelApi checkpointV2ModelApi);

        @Named("mapCheckpointDtoToCheckpoint")
    Checkpoint mapCheckpointDtoToCheckpoint(CheckpointDto source);
//    @Named("mapCheckpointDtoToCheckpointModelApi")
//    CheckpointModelApi mapCheckpointDtoToCheckpointModelApi(CheckpointDto source);
}
