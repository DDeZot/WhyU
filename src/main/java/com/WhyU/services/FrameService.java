package com.WhyU.services;

import com.WhyU.dto.ActionDTO;
import com.WhyU.dto.FrameDTO;
import com.WhyU.models.Action;
import com.WhyU.models.Frame;
import com.WhyU.models.Story;
import com.WhyU.models.enums.EndingType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FrameService {
    public Frame findFrameById(Long id);
    public List<Frame> findAllFrames();
    public Frame createFrame(FrameDTO dto, Long storyID);
    public Frame createFrame(FrameDTO dto, String storyName);
    public Frame updateFrame(Long id, FrameDTO dto);
    public Frame setEnding(Long id, EndingType endingType);
    public void deleteById(Long id);
    public Action addAction(Long frameID, ActionDTO dto);
    public List<Action> getAllActions(Long id);
    public Frame uploadImageToFrame(Long id, MultipartFile image) throws IOException;
}
