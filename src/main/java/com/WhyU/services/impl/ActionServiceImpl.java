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

import java.util.List;

@Service
public class ActionServiceImpl implements ActionService {
    private final ActionRepository actionRepository;
    private final FrameRepository frameRepository;

    @Autowired
    public ActionServiceImpl(ActionRepository actionRepository, FrameRepository frameRepository){
        this.actionRepository = actionRepository;
        this.frameRepository = frameRepository;
    }

    public Action findActionById(Long id){
        return actionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Действие с id " + id + " не найдено!"));
    }

    public List<Action> findActionsByConsequenceId(Long consequenceID) {
        return List.of();
    }

    public List<Action> findAllActions(){
        return actionRepository.findAll();
    }

    public Action createAction(ActionDTO dto, Long frameID){
        Frame frame = frameRepository.findById(frameID)
                .orElseThrow(() -> new EntityNotFoundException("Кадр с id " + frameID + " не найден!"));

        Action action = Action.builder()
                .frame(frame)
                .head(dto.getHead())
                .build();

        frame.addAction(action);
        frameRepository.save(frame);

        return actionRepository.save(action);
    }

    public Action updateAction(Long id, ActionDTO dto){
        Action action = actionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Действие с id " + id + " не найдено!"));

        if(dto.getHead() != null)
            action.setHead(dto.getHead());

        if(dto.getFrame() != null)
            action.setFrame(dto.getFrame());

        if(dto.getConsequence() != null)
            action.setConsequence(dto.getConsequence());

        return actionRepository.save(action);
    }

    public void deleteActionById(Long id) {

    }
}
