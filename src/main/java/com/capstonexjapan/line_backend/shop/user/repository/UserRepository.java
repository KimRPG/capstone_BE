package com.capstonexjapan.line_backend.shop.user.repository;

import com.capstonexjapan.line_backend.shop.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
