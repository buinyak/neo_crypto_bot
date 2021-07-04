package com.gb.neobot.repository;

import com.gb.neobot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByChatId(long id);
    User findByid(int id);
}
