package com.tuflex.web.user.payload.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.tuflex.web.user.model.Hotel;
import com.tuflex.web.user.model.Product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {
    private Long pid;
    private String name, imageUrl, information;
    private int price;

    static public ProductDto toDto(Product product) {
        return new ProductDto(product.getPid(), product.getName(),
                product.getImageUrl(), product.getInformation(), product.getPrice());
    }
}
