package com.WhyU.services.impl;

import com.WhyU.dto.ActionDTO;
import com.WhyU.models.Action;
import com.WhyU.models.Frame;
import com.WhyU.repositories.ActionRepository;
import com.WhyU.repositories.FrameRepository;
import com.WhyU.services.ActionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActionServiceImpl implements ActionService {
    private final ActionRepository actionRepository;
    private final FrameRepository frameRepository;
    private final FrameServiceImpl frameService;

    @Autowired
    public ActionServiceImpl(ActionRepository actionRepository, FrameRepository frameRepository, FrameServiceImpl frameService){
        this.actionRepository = actionRepository;
        this.frameRepository = frameRepository;
        this.frameService = frameService;
    }

    public Action findActionById(Long id){
        return actionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Действие с id " + id + " не найдено!"));
    }

    public List<Action> findAllActionsByConsequenceId(Long consequenceID) {
        return actionRepository.findByConsequenceId(consequenceID);
    }

    public List<Action> findAllActions(){
        return actionRepository.findAll();
    }

    @Transactional
    public Action createAction(ActionDTO dto, Long frameID){
        Frame frame = frameService.findFrameById(frameID);

        Action action = Action.builder()
                .frame(frame)
                .head(dto.getHead())
                .build();

        Frame consequence = Frame.builder().story(frame.getStory()).build();
        frameRepository.save(consequence);

        action.setConsequence(consequence);
        actionRepository.save(action);

        frameRepository.save(frame);
        frameRepository.save(consequence);

        return actionRepository.save(action);
    }

    public Action updateAction(Long id, ActionDTO dto){
        Action action = findActionById(id);

        if(dto.getHead() != null)
            action.setHead(dto.getHead());

        if(dto.getFrame() != null)
            action.setFrame(dto.getFrame());

        if(dto.getConsequence() != null)
            action.setConsequence(dto.getConsequence());

        return actionRepository.save(action);
    }

    public Frame getConsequence(Long id) {
        Action action = findActionById(id);

        return action.getConsequence();
    }

    public void deleteActionById(Long id) {
        actionRepository.deleteById(id);
    }
}
