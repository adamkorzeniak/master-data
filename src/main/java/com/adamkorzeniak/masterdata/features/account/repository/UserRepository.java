package com.adamkorzeniak.masterdata.features.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adamkorzeniak.masterdata.features.account.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);

}
