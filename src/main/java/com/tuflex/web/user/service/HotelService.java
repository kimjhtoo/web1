package com.tuflex.web.user.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.tuflex.web.user.model.Cart;
import com.tuflex.web.user.model.Hotel;
import com.tuflex.web.user.model.Payment;
import com.tuflex.web.user.model.Product;
import com.tuflex.web.user.model.User;
import com.tuflex.web.user.payload.dto.CartDto;
import com.tuflex.web.user.payload.dto.HotelDto;
import com.tuflex.web.user.payload.dto.ReviewDto;
import com.tuflex.web.user.payload.request.CartRequest;
import com.tuflex.web.user.repository.CartRepository;
import com.tuflex.web.user.repository.HotelRepository;
import com.tuflex.web.user.repository.ProductRepository;
import com.tuflex.web.user.repository.ReviewRepository;
import com.tuflex.web.user.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Value("${upload.image.location}")
    private String imageFolder;

    @Value("${get.image.location}")
    private String imageUrl;

    @Transactional
    public Hotel findByPid(Long pid) {
        return hotelRepository.findById(pid).orElseThrow();
    }

    @Transactional
    public Product findProudctByPid(Long pid) {
        return productRepository.findById(pid).orElseThrow();
    }

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<ReviewDto> getReviews(Hotel hotel) {
        return reviewRepository.findByHotel(hotel).stream().map(r -> ReviewDto.toDto(r)).toList();
    }

    public long getRatingCount(Hotel hotel, int rate) {
        return reviewRepository.countByHotelAndRate(hotel, rate);
    }

    // @Transactional
    // public ProductDto findProductDetail(Long pid) {
    // return ProductDto.toDto(findDetailByPid(pid));
    // }

    @Transactional
    public long getCount(String searchType, String searchWord) {
        return hotelRepository.count();
    }

    @Transactional
    public List<Hotel> findHotels(String address) {
        List<Hotel> hotels = hotelRepository.findAll(Sort.by(Sort.Direction.DESC, "regDt"));
        return hotels.stream().filter(h -> h.getAddress().contains(address)).toList();
    }

    public List<HotelDto> searchHotelOnMap(Double lat, Double lon) {
        System.out.println("lat: " + lat + " , lon: " + lon);
        List<Hotel> companyList = hotelRepository.findByLocation(lat, lon);
        return companyList.stream().map(c -> HotelDto.toDto(c)).toList();
    }

    @Transactional
    public void cart(Long pid, CartRequest req) {
        User user = userRepository.findById(pid).orElseThrow();
        Product product = productRepository.findById(req.getRid()).orElseThrow();
        Cart cart = new Cart(product, user, req.getStartDate(), req.getEndDate(), req.getAdult(), req.getChild());
        cartRepository.save(cart);
    }

    @Transactional
    public void cartDelete(Long pid, Long cid) {
        User user = userRepository.findById(pid).orElseThrow();
        Cart cart = cartRepository.findById(cid).orElseThrow();
        cartRepository.delete(cart);
    }

    public List<CartDto> getCart(Long pid) {
        List<Cart> carts = cartRepository.findByUser(userRepository.findById(pid).orElseThrow());
        return carts.stream().map(c -> CartDto.toDto(c)).toList();
    }
}
