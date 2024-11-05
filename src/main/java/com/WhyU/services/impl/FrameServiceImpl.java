package com.WhyU.services.impl;

import com.WhyU.dto.ActionDTO;
import com.WhyU.dto.FrameDTO;
import com.WhyU.models.Action;
import com.WhyU.models.Attachment;
import com.WhyU.models.Frame;
import com.WhyU.models.Story;
import com.WhyU.models.enums.EndingType;
import com.WhyU.repositories.ActionRepository;
import com.WhyU.repositories.FrameRepository;
import com.WhyU.repositories.StoryRepository;
import com.WhyU.services.FrameService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FrameServiceImpl implements FrameService {
    private final FrameRepository frameRepository;
    private final StoryRepository storyRepository;
    private final AttachmentServiceImpl attachmentServiceImpl;
    private final ActionRepository actionRepository;

    @Autowired
    public FrameServiceImpl(FrameRepository frameRepository, StoryRepository storyRepository, AttachmentServiceImpl attachmentServiceImpl, ActionRepository actionRepository){
        this.frameRepository = frameRepository;
        this.storyRepository = storyRepository;
        this.attachmentServiceImpl = attachmentServiceImpl;
        this.actionRepository = actionRepository;
    }

    public Frame createFrame(FrameDTO dto, Long storyID){
        Story story = storyRepository.findById(storyID)
                .orElseThrow(() -> new EntityNotFoundException("История с ID " + storyID + " не найдена"));

        Frame frame = Frame.builder()
                .head(dto.getHead())
                .description(dto.getDescription())
                .story(story)
                .build();

        storyRepository.save(story);
        return frameRepository.save(frame);
    }

    public Frame createFrame(FrameDTO dto, String storyName) {
        Story story = storyRepository.findStoryByHead(storyName)
                .orElseThrow(() -> new EntityNotFoundException("История с именем " + storyName + " не найдена"));

        Frame frame = Frame.builder()
                .head(dto.getHead())
                .description(dto.getDescription())
                .story(story)
                .build();

        story.addFrame(frame);
        storyRepository.save(story);
        return frameRepository.save(frame);
    }

    public Frame findFrameById(Long id){
        return frameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Кадр с ID " + id + " не найден"));
    }

    public Frame findFrameByGateId(Long gateID) {
        return null;
    }

    public List<Frame> findAllFrames(){
        return frameRepository.findAll();
    }

    public void deleteById(Long id){
        frameRepository.deleteById(id);
    }

    public Action addAction(Long frameID, ActionDTO dto) {
        Frame frame = frameRepository.findById(frameID)
                .orElseThrow(() -> new EntityNotFoundException("Кадр с id " + frameID + " не найден!"));

        frame.setEnding(false);
        frame.setEndingType(null);

        Action action = Action.builder()
                .frame(frame)
                .head(dto.getHead())
                .build();

        Frame consequence = Frame.builder().story(frame.getStory()).build();
        frameRepository.save(consequence);

        action.setConsequence(consequence);
        actionRepository.save(action);

        consequence.setGate(action);

        frameRepository.save(frame);
        frameRepository.save(consequence);

        return actionRepository.save(action);
    }

    public Action addAction(Long frameID, Action action) {
        Frame frame = frameRepository.findById(frameID)
                .orElseThrow(() -> new EntityNotFoundException("Кадр с id " + frameID + " не найден!"));

        frame.setEnding(false);
        frame.setEndingType(null);

        Frame consequence = Frame.builder().story(frame.getStory()).build();
        frameRepository.save(consequence);

        action.setConsequence(consequence);
        actionRepository.save(action);

        consequence.setGate(action);

        frameRepository.save(frame);
        frameRepository.save(consequence);

        return actionRepository.save(action);
    }

    public Frame updateFrame(Long id, FrameDTO dto){
        Frame frame = frameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Кадр с ID " + id + " не найден"))  ;

        if(dto.getHead() != null)
            frame.setHead(dto.getHead());

        if(dto.getDescription() != null)
            frame.setDescription(dto.getDescription());

        if(dto.getAttachment() != null)
            frame.setAttachment(dto.getAttachment());

        Story story = frame.getStory();
        story.setUpdatedAt(LocalDateTime.now());

        storyRepository.save(story);

        return frameRepository.save(frame);
    }

    public Frame setEnding(Long id, EndingType endingType) {
        Frame frame = frameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Кадр с id " + id + " не найден"));

        frame.setEndingType(endingType);
        return frameRepository.save(frame);
    }

    public Frame uploadImageToFrame(Long id, MultipartFile image) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null");
        }

        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Изображение не может быть пустым");
        }

        Frame frame = frameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Кадр с ID " + id + " не найден"));

        Attachment attachment = attachmentServiceImpl.createAttachment(image);

        frame.setAttachment(attachment);
        return frameRepository.save(frame);
    }
}
