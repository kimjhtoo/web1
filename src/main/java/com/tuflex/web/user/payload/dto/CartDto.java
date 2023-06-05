package com.tuflex.web.user.payload.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.tuflex.web.user.model.Cart;
import com.tuflex.web.user.model.Hotel;
import com.tuflex.web.user.model.Product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartDto {
    private Long pid;
    private String name, startDate, endDate;
    private int adult, child, price;

    static public CartDto toDto(Cart cart) {
        return new CartDto(cart.getPid(), cart.getProduct().getHotel().getName(), cart.getStartDate(),
                cart.getEndDate(), cart.getAdult(), cart.getChild(), cart.getProduct().getPrice());
    }
}
