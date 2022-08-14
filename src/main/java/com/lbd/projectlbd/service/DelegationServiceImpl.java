package com.lbd.projectlbd.service;

import com.lbd.projectlbd.dto.DelegationDto;
import com.lbd.projectlbd.dto.DelegationListDto;
import com.lbd.projectlbd.entity.Checkpoint;
import com.lbd.projectlbd.entity.CommentToCheckpoint;
import com.lbd.projectlbd.entity.Delegation;
import com.lbd.projectlbd.exception.DelegationValidationException;
import com.lbd.projectlbd.exception.InvalidParamException;
import com.lbd.projectlbd.mapper.DelegationMapper;
import com.lbd.projectlbd.repository.DelegationRepository;
import com.lbd.projectlbd.repository.MasterdataCheckpointRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DelegationServiceImpl implements DelegationService {

    private final DelegationRepository delegationRepository;
    private final MasterdataCheckpointRepository masterdataCheckpointRepository;
    private final DelegationMapper delegationMapper;
    private final Logger logger = LoggerFactory.getLogger(DelegationServiceImpl.class);
    private final ExpressionParser expressionParser = new SpelExpressionParser();

    private final CheckpointService checkpointService;

    /** Utilities
     * */
    private boolean shouldCheckpointBeAddedToDelegation(Delegation delegation, String spelExpression){
        EvaluationContext context = new StandardEvaluationContext(delegation);
        try{
            return Boolean.TRUE.equals(
                    expressionParser
                            .parseExpression(spelExpression)
                            .getValue(context, Boolean.class));
        }catch(Exception exception){
            logger.info("Invalid spel expression: " +spelExpression);
            return false;
        }
    }

    private List<Checkpoint> getCheckpointsOfDelegationFromMasterData(Delegation delegation) {
        List<Checkpoint> checkpointsOfDelegation = new ArrayList<>();

        masterdataCheckpointRepository.findAll().forEach(masterdataCheckpoint -> {

            if (shouldCheckpointBeAddedToDelegation(delegation, masterdataCheckpoint.getSpelExpression())) {
                checkpointsOfDelegation.add(
                        Checkpoint.builder()
                                .masterDataCheckpointId(masterdataCheckpoint.getId())
                                .delegation(delegation)
                                .description(masterdataCheckpoint.getDescription())
//                                .comment("added automatically")

                                .isChecked(false)
                                .build()
                );
            }

        });

        return checkpointsOfDelegation;
    }

    private boolean isDelegationValid(DelegationDto delegationDto){
        if(delegationDto.getStartDate().isBefore(LocalDate.now())){
            throw new DelegationValidationException("The delegation cannot include the start date as a past date.");
        }
        if(delegationDto.getEndDate().isBefore(delegationDto.getStartDate())){
            throw new DelegationValidationException("The start date must be before the end date.");
        }
        if(!Arrays.asList(Locale.getISOCountries()).contains(delegationDto.getCountryCode())){
            throw new DelegationValidationException("Invalid country code");
        }
        if(delegationDto.getCity().isEmpty()){
            throw new DelegationValidationException("Invalid city name");
        }
        return true;
    }

    /** Public
     * */
    @Override public Delegation findById(Long id) {
        return delegationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delegation with id="+id+" not found!"));
    }

    @Override public DelegationDto findDtoById(Long id) {
        return delegationMapper.mapDelegationToDelegationDTO(delegationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delegation with id="+id+" not found!")));
    }

    @Override @Transactional
    public void add(DelegationDto delegationDTO) {

        Delegation delegationToAdd = delegationMapper.mapDelegationDtoToDelegation(delegationDTO);

        if(isDelegationValid(delegationDTO)){
            delegationToAdd.setCheckpointSet(
                    getCheckpointsOfDelegationFromMasterData(delegationToAdd)
            );

            Delegation delegation =delegationRepository.save(delegationToAdd);
            checkpointService.setInitialComment(delegation.getCheckpointSet());
        }
    }

    @Override @Transactional
    public void delete(Long id) {
        delegationRepository.deleteById(id);
    }

    @Override @Transactional public void update(Long delegationId, @Valid DelegationDto delegationDto) {
        Delegation updatedDelegation = delegationMapper.updateDelegation(findById(delegationId), delegationDto);
        updatedDelegation.setCheckpointSet(
                getCheckpointsOfDelegationFromMasterData(updatedDelegation)
        );
        delegationRepository.save(updatedDelegation);
    }

    @Override
    public List<DelegationDto> getAll() {
        return delegationRepository.findAll()
                .stream()
                .map(delegationMapper::mapDelegationToDelegationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DelegationListDto getAllPaginated(Integer size, Integer page, String sort, String order) {
        Sort sortOrder = null;
        if(order.toLowerCase().equals("desc")){
            sortOrder=Sort.by(sort).descending();
        }else if(order.toLowerCase().equals("asc")){
            sortOrder=Sort.by(sort).ascending();
        }

        if(size<=0)
            throw new InvalidParamException("Size "+size+" not valid");
        if(page<0)
            throw new InvalidParamException("Page "+page+" not valid");

        try {
            Pageable pageable = PageRequest.of(page,size, sortOrder);
            Page<DelegationDto> delegationPage=(delegationRepository.findAll(pageable)).map(x-> delegationMapper.mapDelegationToDelegationDTO(x));

            return new DelegationListDto(delegationPage.getTotalElements(),delegationPage.getTotalPages(),delegationPage.getContent(),page);
        }catch (PropertyReferenceException ex){
            throw new InvalidParamException("Column "+sort+" not found");
        }catch (IllegalArgumentException ex){
            throw new InvalidParamException("Order "+order+" not valid");
        }catch (Exception ex){
            throw new InvalidParamException("Something went wrong");
        }

    }

}
