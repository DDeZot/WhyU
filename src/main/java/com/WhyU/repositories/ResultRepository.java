package com.WhyU.repositories;

import com.WhyU.models.Result;
import com.WhyU.models.enums.FrameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    @Transactional(readOnly = true)
    @Query("SELECT r FROM Result r WHERE r.ending.id = :endingFrameID")
    List<Result> findAllByEndingId(Long endingFrameID);

    @Transactional(readOnly = true)
    @Query("SELECT r FROM Result r WHERE r.story.id = :storyID")
    List<Result> findAllByStoryId(Long storyID);

    @Transactional(readOnly = true)
    @Query("SELECT r FROM Result r WHERE r.user.id = :userID")
    List<Result> findAllByUserId(Long userID);

    @Transactional(readOnly = true)
    @Query("SELECT r FROM Result r WHERE r.story.id = :storyID AND r.ending.id = :endingFrameID")
    List<Result> findAllByStoryIdAndEndingId(Long storyID, Long endingFrameID);

    @Transactional(readOnly = true)
    @Query("SELECT r FROM Result r WHERE r.user.id = :userID AND r.ending.frameType = :frameType")
    List<Result> findAllByUserIDAndEndingType(Long userID, FrameType frameType);
}
