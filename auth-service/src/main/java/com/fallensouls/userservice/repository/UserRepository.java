package com.fallensouls.userservice.repository;

import com.fallensouls.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public interface UserRepository extends JpaRepository<User, UUID> {

//    User findById(String id);
}
