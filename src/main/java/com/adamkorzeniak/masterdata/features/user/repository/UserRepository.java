package com.adamkorzeniak.masterdata.features.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adamkorzeniak.masterdata.features.user.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
