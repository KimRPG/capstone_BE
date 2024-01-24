package com.capstonexjapan.line_backend.linePay.line.repository;

import com.capstonexjapan.line_backend.linePay.line.domain.entity.LineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<LineEntity,Long> {
}
