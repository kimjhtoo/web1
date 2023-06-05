package com.tuflex.web.user.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.transaction.Transactional;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.web.multipart.MultipartFile;

import com.tuflex.web.common.CommonVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends CommonVO {
    private String lastTransactionKey, paymentKey, requestedAt, approvedAt;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private String orderName;

    private String method;

    private String receipt;

    private LocalDateTime refundDt, doneDt, chargedDt;
    private String refundMethod;
    private Long refundPrice;

    private String cancelType, cancelContent;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private User user;

    public Payment(String lastTransactionKey, String paymentKey, String requestedAt, String approvedAt, Long amount,
            String orderId, String orderName, String method, String receipt, Boolean refund, User user) {
        this.lastTransactionKey = lastTransactionKey;
        this.paymentKey = paymentKey;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
        this.amount = amount;
        this.orderId = orderId;
        this.orderName = orderName;
        this.method = method;
        this.receipt = receipt;
        this.user = user;
    }
}