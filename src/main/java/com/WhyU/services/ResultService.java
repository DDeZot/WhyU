package com.WhyU.services;

import com.WhyU.dto.ResultDTO;
import com.WhyU.models.Result;

import java.util.List;

public interface ResultService {
    List<Result> findAllResults();
    List<Result> findAllResultsByStoryId(Long storyID);
    List<Result> findAllResultsByEndingId(Long endingFrameId);
    List<Result> findAllResultsByUserId(Long userID);
    Result findResultById(Long id);
    Result createResult(ResultDTO dto);
    Result updateResult(Long id, ResultDTO dto);
}
