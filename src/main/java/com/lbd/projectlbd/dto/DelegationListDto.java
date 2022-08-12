package com.lbd.projectlbd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DelegationListDto {

    private Long totalCount;
    private Integer count;
    private List<DelegationDto> result;
    private Integer page;

}
