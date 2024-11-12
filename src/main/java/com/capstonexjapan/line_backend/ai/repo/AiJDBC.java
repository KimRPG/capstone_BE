package com.capstonexjapan.line_backend.ai.repo;

import com.capstonexjapan.line_backend.ai.entity.Recommend;
import com.capstonexjapan.line_backend.shop.product.entity.Product;
import com.capstonexjapan.line_backend.shop.product.entity.ProductStatus;
import com.capstonexjapan.line_backend.shop.product.repository.ProductInfoMapping;
import com.capstonexjapan.line_backend.shop.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AiJDBC {
    private final JdbcTemplate jdbcTemplate;
    private final StoreService storeService;

    public List<Recommend> check(float[] embedding) {
        // embedding 배열을 PostgreSQL에서 사용할 수 있는 벡터 형식의 문자열로 변환
        String embeddingStr = arrayToPostgresVectorFormat(embedding);

        // SQL 쿼리 작성
        String sql = "SELECT id, name, embedding, embedding<-> CAST(? AS vector) AS similarity " +
                "FROM recommend " +
                "ORDER BY similarity ASC " +
                "LIMIT 1";

        // 쿼리 실행 및 결과 매핑
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            return Recommend.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .embedding(embedding)  // 조회 시 원본 임베딩 배열 사용
                    .build();
        }, embeddingStr);
    }

    // float 배열을 PostgreSQL 벡터 형식으로 변환하는 유틸리티 메서드
    private String arrayToPostgresVectorFormat(float[] embedding) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < embedding.length; i++) {
            sb.append(embedding[i]);
            if (i < embedding.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public List<Product> recommendProduct(float[] embedding) {
        // embedding 배열을 PostgreSQL에서 사용할 수 있는 벡터 형식의 문자열로 변환
        String embeddingStr = arrayToPostgresVectorFormat(embedding);

        // SQL 쿼리 작성
        String sql = "SELECT * , embedding <-> CAST(? AS vector) AS similarity " +
                "FROM product " +
                "ORDER BY similarity ASC " +
                "LIMIT 5";

        // 쿼리 실행 및 결과 매핑
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            return Product.builder()
                    .productId(resultSet.getLong("product_id"))
                    .amount(resultSet.getInt("amount"))
                    .brand(resultSet.getString("brand"))
                    .createdAt(resultSet.getDate("created_at"))
                    .description(resultSet.getString("description"))
                    .discount(resultSet.getInt("discount"))
                    .imageUrl(resultSet.getString("image_url"))
                    .name(resultSet.getString("name"))
                    .price(resultSet.getInt("price"))
                    .status(ProductStatus.valueOf(resultSet.getString("status")))
                    .updatedAt(resultSet.getDate("updated_at"))
                    .store(storeService.findById(resultSet.getLong("store_id")))
                    // 조회 시 원본 임베딩 배열 사용
                    .build();
        }, embeddingStr);
    }
}
