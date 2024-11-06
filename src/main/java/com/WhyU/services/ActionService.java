package com.WhyU.services;

import com.WhyU.dto.ActionDTO;
import com.WhyU.models.Action;
import com.WhyU.models.Frame;

import java.util.List;

public interface ActionService {
    Action findActionById(Long id);
    List<Action> findAllActionsByConsequenceId(Long consequenceID);
    List<Action> findAllActions();
    Action createAction(ActionDTO dto, Long frameID);
    Action updateAction(Long id, ActionDTO dto);
    Frame getConsequence(Long id);
    void deleteActionById(Long id);
}
