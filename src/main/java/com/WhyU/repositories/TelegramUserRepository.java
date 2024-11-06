package com.WhyU.repositories;

import com.WhyU.models.TelegramUser;
import jakarta.persistence.Transient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {
    @Transactional(readOnly = true)
    @Query("SELECT tu from TelegramUser tu WHERE tu.telegramID = :tgID")
    Optional<TelegramUser> findByTelegramID(Long tgID);
}
