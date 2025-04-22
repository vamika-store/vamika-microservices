package com.vamika.User.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vamika.User.entities.User;

@Repository
public interface UserDetailRepository extends JpaRepository<User,UUID> {
	User findByEmail(String email);
}
