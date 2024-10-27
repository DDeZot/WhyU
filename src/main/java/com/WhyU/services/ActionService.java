package com.WhyU.services;

import com.WhyU.dto.ActionDTO;
import com.WhyU.models.Action;

import java.util.List;

public interface ActionService {
    public Action findActionById(Long id);
    public List<Action> findActionsByConsequenceId(Long consequenceID);
    public List<Action> findAllActions();
    public Action createAction(ActionDTO dto, Long frameID);
    public Action updateAction(Long id, ActionDTO dto);
    public void deleteActionById(Long id);
}
