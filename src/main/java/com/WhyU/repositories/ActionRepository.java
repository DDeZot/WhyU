package com.WhyU.repositories;

import com.WhyU.models.Action;
import com.WhyU.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
}
