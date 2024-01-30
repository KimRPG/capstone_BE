package com.capstonexjapan.line_backend.linePay.line.controller.request;

import com.capstonexjapan.line_backend.linePay.line.domain.entity.LinePayRequestEntity;
import com.capstonexjapan.line_backend.linePay.line.domain.entity.Packages;
import com.capstonexjapan.line_backend.linePay.line.domain.entity.RedirectUrls;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LineRequestRequest {
    private Integer amount;
    private String currency;
    private String orderId;
    private List<Packages> packages;
    private RedirectUrls redirectUrls;

    public LinePayRequestEntity toEntity() {
        return LinePayRequestEntity.builder()
                .orderId(this.orderId)
                .amount(this.amount)
                .currency(this.currency)
                .packages(this.packages)
                .redirectUrls(this.redirectUrls)
                .build();
    }
}


