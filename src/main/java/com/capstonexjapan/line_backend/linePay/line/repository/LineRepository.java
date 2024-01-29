package com.capstonexjapan.line_backend.linePay.line.repository;

import com.capstonexjapan.line_backend.linePay.line.domain.entity.LinePayRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<LinePayRequestEntity,Long> {
}
