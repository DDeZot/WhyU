package com.WhyU.services;

import com.WhyU.dto.FrameDTO;
import com.WhyU.models.Frame;
import com.WhyU.models.Story;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FrameService {
    public Frame findFrameById(Long id);
    public Frame findFrameByGateId(Long gateID);
    public List<Frame> findAllFrames();
    public Frame createFrame(FrameDTO dto, Long storyID);
    public Frame updateFrame(Long id, FrameDTO dto);
    public void deleteById(Long id);
    public void uploadImageToFrame(Long id, MultipartFile image) throws IOException;
}
