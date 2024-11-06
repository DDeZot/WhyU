package com.WhyU.repositories;

import com.WhyU.models.Story;
import com.WhyU.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
    @Transactional(readOnly = true)
    Optional<Story> findStoryByHead(String head);
}
