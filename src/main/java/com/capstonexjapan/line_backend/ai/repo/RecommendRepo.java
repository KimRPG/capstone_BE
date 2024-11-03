package com.capstonexjapan.line_backend.ai.repo;

import com.capstonexjapan.line_backend.ai.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RecommendRepo extends JpaRepository<Recommend, Integer> {

}
