package com.datawise.satisactual.repository;

import com.datawise.satisactual.entities.UserMessage;
import com.datawise.satisactual.entities.UserMessageEmbeddedKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMessageRepository extends JpaRepository<UserMessage, UserMessageEmbeddedKey> {
    UserMessage findByIdAndCodRecStatus(UserMessageEmbeddedKey id, String recStatus);
}
