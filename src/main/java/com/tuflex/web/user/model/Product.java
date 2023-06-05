package com.tuflex.web.user.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.tuflex.web.common.CommonVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class Product extends CommonVO {
    @Size(max = 30)
    private String name;

    @Size(max = 5000)
    private String information;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Boolean status;

    @ColumnDefault("0")
    private Long likeCount, sellCount, reviewCount;

    private int price;

    @ColumnDefault("0.0")
    private Double rate;

    private String imageUrl, imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_pid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Hotel hotel;

    // public double getRate() {
    // return rate
    // double rate = 0;
    // int reviewCount = 0;
    // for (ProductDetail productDetail : productDetails) {
    // rate += productDetail.getRate();
    // reviewCount += productDetail.getReviewCount();
    // }
    // if (reviewCount == 0) {
    // return 0;
    // } else {
    // return rate / reviewCount;
    // }
    // }
}