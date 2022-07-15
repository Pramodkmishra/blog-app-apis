package com.pmd.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmd.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
