package com.sogeti.hamda.ben.fadhel.demo.repositories;


import com.sogeti.hamda.ben.fadhel.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmailAndPassword(String email, String password);
}
