package com.WhyU.repositories;

import com.WhyU.models.Frame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface FrameRepository extends JpaRepository<Frame, Long> {
    @Query("SELECT f FROM Frame f WHERE f.gate.id = :gateId")
    @Transactional(readOnly = true)
    public  Optional<Frame> findByGate(Long gateId);
}