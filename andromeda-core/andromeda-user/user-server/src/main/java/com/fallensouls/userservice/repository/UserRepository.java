package com.fallensouls.userservice.repository;

import com.fallensouls.common.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public interface UserRepository extends JpaRepository<User, UUID> {

    @Modifying
    @Query("update User set islocked = true where id = ?1")
    void lockUserById(UUID id);
}
