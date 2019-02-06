package com.adamkorzeniak.masterdata.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adamkorzeniak.masterdata.security.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);

}
