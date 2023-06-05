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
public class HotelDto {
        private Long pid;
        private String category, name, regDt, imageUrl;
        private int price;
        private String rating;
        double latitude, longitude;
        private List<ProductDto> products;

        static public HotelDto toDto(Hotel hotel) {
                return new HotelDto(hotel.getPid(), hotel.getCategory(), hotel.getName(),
                                hotel.getRegDt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm")),
                                hotel.getUrl1(),
                                hotel.getProducts().stream().filter(p -> p.getIsEnable()).count() == 0 ? 0
                                                : (int) ((hotel.getProducts().get(0).getPrice()
                                                                + hotel.getProducts().get(1).getPrice()
                                                                + hotel.getProducts().get(2).getPrice())
                                                                / hotel.getProducts().stream()
                                                                                .filter(p -> p.getIsEnable()).count()),
                                String.format("%.1f", hotel.getReviews().size() == 0 ? 0
                                                : (double) hotel.getReviews().stream().map(h -> h.getRate())
                                                                .mapToInt(Integer::intValue).sum()
                                                                / hotel.getReviews().size()),
                                hotel.getLatitude(), hotel.getLongitude(),
                                hotel.getProducts().stream().filter(p -> p.getIsEnable()).map(p -> ProductDto.toDto(p))
                                                .toList());
        }
}
