package com.WhyU.repositories;

import com.WhyU.models.Action;
import com.WhyU.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
    @Transactional(readOnly = true)
    @Query("SELECT a FROM Action a WHERE a.frame.id = :frameID")
    public List<Action> findAllByFrameId(Long frameID);

    @Transactional(readOnly = true)
    @Query("SELECT a FROM Action a WHERE a.consequence.id = :consequenceID")
    public Optional<Action> findByConsequenceId(Long consequenceID);
}
