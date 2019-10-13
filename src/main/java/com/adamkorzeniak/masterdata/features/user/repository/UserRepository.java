package com.adamkorzeniak.masterdata.features.user.repository;

import com.adamkorzeniak.masterdata.features.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
