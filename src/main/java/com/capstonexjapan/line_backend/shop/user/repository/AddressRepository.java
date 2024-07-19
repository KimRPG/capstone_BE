package com.capstonexjapan.line_backend.shop.user.repository;

import com.capstonexjapan.line_backend.shop.user.entity.Address;
import com.capstonexjapan.line_backend.shop.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(UserEntity user);
}
