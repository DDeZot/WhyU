package com.WhyU.repositories;

import com.WhyU.models.Attachment;
import com.WhyU.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    @Transactional(readOnly = true)
    public Optional<Attachment> findByHashCode(Integer hashCode);
}
