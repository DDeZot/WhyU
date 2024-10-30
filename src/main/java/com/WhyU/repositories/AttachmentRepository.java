package com.WhyU.repositories;

import com.WhyU.models.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    @Transactional(readOnly = true)
    List<Attachment> findAllByLength(Long length);
}
