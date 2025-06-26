package com.WhyU.services.impl;

import com.WhyU.dto.FrameDTO;
import com.WhyU.dto.StoryDTO;
import com.WhyU.models.Attachment;
import com.WhyU.models.Frame;
import com.WhyU.models.Story;
import com.WhyU.models.enums.FrameType;
import com.WhyU.repositories.FrameRepository;
import com.WhyU.repositories.StoryRepository;
import com.WhyU.services.StoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class StoryServiceImpl implements StoryService {
    private final AttachmentServiceImpl attachmentServiceImpl;
    private final StoryRepository storyRepository;
    private final FrameRepository frameRepository;

    @Autowired
    public StoryServiceImpl(StoryRepository storyRepository, AttachmentServiceImpl attachmentServiceImpl, FrameRepository frameRepository){
        this.storyRepository = storyRepository;
        this.attachmentServiceImpl = attachmentServiceImpl;
        this.frameRepository = frameRepository;
    }

    public Story findStoryById(Long id){
        return storyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("История с ID " + id + " не найдена"));
    }

    public Story findStoryByHead(String head){
        return storyRepository. findStoryByHead(head)
                .orElseThrow(() -> new EntityNotFoundException("История с именем " + head + " не найдена"));
    }

    @Cacheable("latest_story")
    public Story findLatestStory() {
        List<Story> allStories = findAllStories();
        if(allStories == null || allStories.isEmpty())
            return null;

        return allStories.stream().max(Comparator.comparing(Story::getCreatedAt))
                .orElseThrow(() -> new EntityNotFoundException("Не найдено истории недели"));
    }

    @Scheduled(cron = "0 0 0 * * *")
    @CachePut("latest_story")
    public void updateLatestStory() {
        findLatestStory();
    }

    public List<Story> findAllStories(){
        return storyRepository.findAll();
    }

    public Story createStory(StoryDTO dto){
        if(storyRepository.findStoryByHead(dto.getHead()).isPresent()){
            return null;
        }

        return storyRepository.save(Story.builder()
                .createdBy(dto.getCreatedBy())
                .head(dto.getHead())
                .description(dto.getDescription())
                .build());
    }

    public Story updateStory(Long id, StoryDTO dto) {
        Story story = findStoryById(id);

        story.setUpdatedBy(dto.getUpdatedBy() == null ? story.getUpdatedBy() : dto.getUpdatedBy());
        if (dto.getHead() != null)
            story.setHead(dto.getHead());

        if (dto.getDescription() != null)
            story.setDescription(dto.getDescription());

        if (dto.getUpdatedBy() != null)
            story.setUpdatedBy(dto.getUpdatedBy());

        story.setUpdatedAt(LocalDateTime.now());

        return storyRepository.save(story);
    }

    public void deleteStoryById(Long id){
        storyRepository.deleteById(id);
    }

    public Frame getFirstFrame(Long storyID) {
        List<Frame> frames = findStoryById(storyID).getFrames();

        return  frames.stream().filter(frame -> frame.getFrameType().equals(FrameType.START))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Не найдена точка входа в историю!"));
    }

    public Frame addFrame(Long storyID, FrameDTO dto) {
        Story story = findStoryById(storyID);

        Frame frame = Frame.builder()
                        .head(dto.getHead())
                        .description(dto.getDescription())
                        .story(story)
                        .frameType(dto.getFrameType() == null ? FrameType.STANDARD : dto.getFrameType())
                        .build();

        return frameRepository.save(frame);
    }

    public Frame addFrame(Long storyID, Frame frame) {
        Story story = findStoryById(storyID);
        frame.setStory(story);

        return frameRepository.save(frame);
    }

    public Story uploadImageToStory(Long id, MultipartFile image) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null");
        }

        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Изображение не может быть пустым");
        }

        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("История с ID " + id + " не найдена"));

        Attachment attachment = attachmentServiceImpl.createAttachment(image);

        story.setPreview(attachment);
        return storyRepository.save(story);
    }
}
