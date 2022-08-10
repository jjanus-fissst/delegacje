package com.lbd.projectlbd.service;

import com.lbd.projectlbd.dto.DelegationDto;
import com.lbd.projectlbd.entity.Checkpoint;
import com.lbd.projectlbd.entity.Delegation;
import com.lbd.projectlbd.exception.DelegationValidationException;
import com.lbd.projectlbd.mapper.DelegationMapper;
import com.lbd.projectlbd.repository.DelegationRepository;
import com.lbd.projectlbd.repository.MasterdataCheckpointRepository;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DelegationServiceImpl implements DelegationService{

    @Autowired
    DelegationRepository delegationRepository;
    @Autowired
    MasterdataCheckpointRepository masterdataCheckpointRepository;

    @Autowired
    DelegationMapper mapper;

    @Override public Delegation findById(Long id) {
        return delegationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delegation with id="+id+" not found!"));
    }

    private List<Checkpoint> getCheckpointsOfDelegationFromMasterData(Delegation delegation) {
        // Wykorzystywanie warunkow SPEL do dodawania checkpointow do delegacji
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext(delegation);

        List<Checkpoint> checkpointsOfDelegation = new ArrayList<>();

        masterdataCheckpointRepository.findAll().forEach(masterdataCheckpoint -> {
            if (parser.parseExpression(masterdataCheckpoint.getSpelExpression()).getValue(context, Boolean.class)) {
                Checkpoint checkpoint = new Checkpoint();
                checkpoint.setMasterDataCheckpointId(masterdataCheckpoint.getId());
                checkpoint.setDelegation(delegation);
                checkpoint.setDescription(masterdataCheckpoint.getDescription());
                checkpoint.setComment("added automatically");
                checkpoint.setIsChecked(false);
                checkpointsOfDelegation.add(checkpoint);
            }
        });

        return checkpointsOfDelegation;
    }

    @Override @Transactional
    public void add(DelegationDto delegationDTO) {

        Delegation delegationToAdd = mapper.mapDelegationDtoToDelegation(delegationDTO);

        if(delegationToAdd.getStartDate().before(new Date())){
            throw new DelegationValidationException("The delegation cannot include the start date as a past date.");
        }
        if(delegationToAdd.getEndDate().before(delegationToAdd.getStartDate())){
            throw new DelegationValidationException("The start date must be before the end date.");
        }

        delegationToAdd.setCheckpointSet(
                getCheckpointsOfDelegationFromMasterData(delegationToAdd)
        );

        delegationRepository.save(delegationToAdd);

    }

    @Override @Transactional
    public void delete(Long id) {
        Delegation delegation = findById(id);

        //delegation.getCheckpointSet().forEach(checkpoint -> checkpointService.delete(checkpoint));
        // todo dla komentarza tak samo usuwanie



        delegationRepository.delete(findById(id));
    }

    @Override public void edit(Long delegationId, DelegationDto delegationDTO) {
        Delegation delegation = findById(delegationId);
        delegation.setStartDate(delegationDTO.getStartDate());
        delegation.setEndDate(delegationDTO.getEndDate());
        delegation.setName(delegationDTO.getName());
        delegation.setLastname(delegationDTO.getLastname());
        delegation.setCity(delegationDTO.getCity());
        delegation.setCountryCode(delegationDTO.getCountryCode());
        delegation.setDescription(delegationDTO.getDescription());

        delegationRepository.save(delegation);
    }

    @Override
    public List<Delegation> getAll() {
        return delegationRepository.findAll();
    }
}
