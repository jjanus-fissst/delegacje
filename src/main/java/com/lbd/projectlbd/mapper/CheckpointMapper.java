package com.lbd.projectlbd.mapper;

import com.lbd.projectlbd.dto.CheckpointDto;
import com.lbd.projectlbd.dto.DelegationDTO;
import com.lbd.projectlbd.entity.Checkpoint;
import com.lbd.projectlbd.entity.Delegation;


public class CheckpointMapper {

    public static Checkpoint convertDtoToEntity(CheckpointDto checkpointDto) {
        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setComment(checkpointDto.getComment());
        checkpoint.setIsChecked(checkpointDto.getIsChecked());
        checkpoint.setMasterDataCheckpointId(checkpointDto.getMasterDataCheckpointId());

        return checkpoint;
    }

    public static CheckpointDto convertEntityToDto(Checkpoint checkpoint) {
        CheckpointDto checkpointDto = new CheckpointDto();
        checkpointDto.setDelegationId(checkpoint.getDelegation().getId());
        checkpointDto.setComment(checkpoint.getComment());
        checkpointDto.setIsChecked(checkpoint.getIsChecked());
        checkpointDto.setDescription(checkpoint.getDescription());
        return checkpointDto;
    }

}
