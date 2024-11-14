package com.WhyU.services.impl;

import com.WhyU.dto.ResultDTO;
import com.WhyU.models.Result;
import com.WhyU.repositories.ResultRepository;
import com.WhyU.services.ResultService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {
    private final ResultRepository resultRepository;
    private final UserServiceImpl userService;
    private final FrameServiceImpl frameService;
    private final StoryServiceImpl storyService;

    @Autowired
    public ResultServiceImpl(ResultRepository resultRepository, UserServiceImpl userService, FrameServiceImpl frameService, StoryServiceImpl storyService) {
        this.resultRepository = resultRepository;
        this.userService = userService;
        this.frameService = frameService;
        this.storyService = storyService;
    }

    public List<Result> findAllResults() {
        return resultRepository.findAll();
    }

    public List<Result> findAllResultsByStoryId(Long storyID) {
        return resultRepository.findAllByStoryId(storyID);
    }

    public List<Result> findAllResultsByEndingId(Long endingFrameId) {
        return resultRepository.findAllByEndingId(endingFrameId);
    }

    public List<Result> findAllResultsByUserId(Long userID) {
        return resultRepository.findAllByUserId(userID);
    }

    public Result findResultById(Long id) {
        return resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Результат с id " + id + " не найден"));
    }

    public Result createResult(ResultDTO dto) {
        return resultRepository.save(Result.builder()
                .user(dto.getUser() == null ? userService.findUserById(dto.getUserID()) :
                        dto.getUser())
                .ending(dto.getEnding() == null ? frameService.findFrameById(dto.getEndingFrameID()) :
                        dto.getEnding())
                .story(dto.getStory() == null ? storyService.findStoryById(dto.getStoryID()) :
                        dto.getStory())
                .build());
    }

    public Result updateResult(Long id, ResultDTO dto) {
        Result result = findResultById(id);

        result.setEnding(dto.getEnding() == null ?
                (dto.getEndingFrameID() == null ? result.getEnding() : frameService.findFrameById(dto.getEndingFrameID())) :
                dto.getEnding());

        return resultRepository.save(result);
    }
}
