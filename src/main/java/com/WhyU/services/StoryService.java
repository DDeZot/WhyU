package com.WhyU.services;

import com.WhyU.dto.FrameDTO;
import com.WhyU.dto.StoryDTO;
import com.WhyU.models.Frame;
import com.WhyU.models.Story;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StoryService {
    public Story createStory(StoryDTO dto);
    public Story updateStory(Long id, StoryDTO dto);
    public void deleteStoryById(Long id);
    public Story findStoryById(Long id);
    public Story findStoryByHead(String head);
    public List<Story> findAllStories();
    public Frame getFirstFrame(Long storyID);
    public Frame addFrame(Long storyID, FrameDTO dto);
    public Frame addFrame(Long storyID, Frame frame);
    public Story uploadImageToStory(Long id, MultipartFile image) throws IOException;
}
