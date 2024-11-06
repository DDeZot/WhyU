package com.WhyU.services;

import com.WhyU.dto.FrameDTO;
import com.WhyU.dto.StoryDTO;
import com.WhyU.models.Frame;
import com.WhyU.models.Story;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StoryService {
    Story createStory(StoryDTO dto);
    Story updateStory(Long id, StoryDTO dto);
    void deleteStoryById(Long id);
    Story findStoryById(Long id);
    Story findStoryByHead(String head);
    List<Story> findAllStories();
    Frame getFirstFrame(Long storyID);
    Frame addFrame(Long storyID, FrameDTO dto);
    Frame addFrame(Long storyID, Frame frame);
    Story uploadImageToStory(Long id, MultipartFile image) throws IOException;
}
