package com.tuflex.web.user.payload.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.tuflex.web.user.model.Hotel;
import com.tuflex.web.user.model.Product;
import com.tuflex.web.user.model.Review;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDto {
    private Long pid;
    private String name, content, regDt;
    private int rate;

    static public ReviewDto toDto(Review review) {
        return new ReviewDto(review.getPid(), review.getUser().getName(), review.getContent(),
                review.getRegDt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")), review.getRate());
    }
}
