package com.WhyU.services;

import com.WhyU.dto.AbstractDTO;
import com.WhyU.models.BasicModel;

import java.util.List;

public interface CRUDService<E extends BasicModel, DTO extends AbstractDTO> {
    E create(DTO dto);
    E read(Long id);
    List<E> readAll();
    E update(DTO dto);
    void delete(Long id);
}
