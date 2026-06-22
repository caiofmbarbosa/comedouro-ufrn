package com.ufrn.projeto.webApp.repository;

import com.ufrn.projeto.webApp.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {

    public Optional<User> findByEmail(String email);

}
