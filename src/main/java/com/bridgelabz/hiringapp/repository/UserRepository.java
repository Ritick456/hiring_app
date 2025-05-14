package com.bridgelabz.hiringapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<com.bridgelabz.hiringapp.entity.User, Long> {

    Optional<com.bridgelabz.hiringapp.entity.User> findByEmail(String email);

}
