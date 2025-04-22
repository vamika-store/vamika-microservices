package com.vamika.User.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vamika.User.entities.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, UUID> {
	Authority findByRoleCode(String user);
}
