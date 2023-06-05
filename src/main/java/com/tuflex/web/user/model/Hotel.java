package com.tuflex.web.user.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.web.multipart.MultipartFile;

import com.tuflex.web.common.CommonVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hotel")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class Hotel extends CommonVO {
    @Size(max = 20)
    private String category;

    @NotBlank
    @Size(max = 20)
    private String name;

    @Column(nullable = true)
    private String address;

    private Double latitude, longitude;
    private Integer price;

    @Size(max = 5000)
    private String introduce;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Product> products;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Review> reviews;

    private String url1 = "", uri1 = "";
    private String url2 = "", uri2 = "";
    private String url3 = "", uri3 = "";
    private String url4 = "", uri4 = "";
    private String url5 = "", uri5 = "";
    private String url6 = "", uri6 = "";
    private String url7 = "", uri7 = "";
    private String url8 = "", uri8 = "";
    private String url9 = "", uri9 = "";
    private String url10 = "", uri10 = "";
}
