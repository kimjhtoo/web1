package com.tuflex.web.user.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.RequestParam;

import com.tuflex.web.user.model.ERole;
import com.tuflex.web.user.model.Hotel;
import com.tuflex.web.user.model.Payment;
import com.tuflex.web.user.model.Review;
import com.tuflex.web.user.payload.dto.CartDto;
import com.tuflex.web.user.payload.dto.HotelDto;
import com.tuflex.web.user.payload.dto.ReviewDto;
import com.tuflex.web.user.repository.CartRepository;
import com.tuflex.web.user.repository.PaymentRepository;
import com.tuflex.web.user.repository.UserRepository;
import com.tuflex.web.user.service.HotelService;
import com.tuflex.web.tool.Utils;;

@Controller
public class HomeController {
    @Autowired
    HotelService hotelService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CartRepository cartRepository;

    @GetMapping(value = "index")
    public String index(Model model) {
        System.out.println("index");
        ERole role = Utils.getRole();
        System.out.println(role.equals(ERole.ROLE_USER) ? Utils.getName() : "");
        model.addAttribute("name", role.equals(ERole.ROLE_USER) ? Utils.getName() : "");
        switch (role) {
            case ROLE_USER:
                return "index";
            default:
                return "index";
        }
    }

    @GetMapping(value = "login")
    public String loginView() {
        System.out.println("login");
        ERole role = Utils.getRole();
        switch (role) {
            case ROLE_USER:
                return "redirect:/index";
            default:
                return "login";
        }
    }

    @GetMapping(value = "signup")
    public String register() {
        ERole role = Utils.getRole();
        switch (role) {
            case ROLE_USER:
                return "redirect:/index";
            default:
                return "signup";
        }
    }

    @GetMapping(value = "help")
    public String help(Model model) {
        ERole role = Utils.getRole();
        model.addAttribute("name", role.equals(ERole.ROLE_USER) ? Utils.getName() : "");
        switch (role) {
            case ROLE_USER:
            default:
                return "help";
        }
    }

    @GetMapping(value = "map")
    public String map(Model model, @RequestParam("k") String address, @RequestParam("s") String start,
            @RequestParam("e") String end, @RequestParam("a") String a, @RequestParam("c") String c) throws Exception {
        String url = "https://dapi.kakao.com/v2/local/search/address.json";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK b53c13dec0f5e14191d73356746da94b");
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        HttpEntity request = new HttpEntity(headers);

        // adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("query", address);

        URI endUri = uriBuilder.build().encode().toUri();
        ResponseEntity<String> response = restTemplate.exchange(endUri, HttpMethod.GET, request,
                String.class);
        JSONParser jsonParser = new JSONParser();
        double x, y;
        try {
            x = Double.parseDouble(
                    (String) ((JSONObject) ((JSONObject) ((JSONArray) ((JSONObject) jsonParser
                            .parse(response.getBody()))
                            .get("documents")).get(0)).get("address")).get("x"));
            y = Double.parseDouble(
                    (String) ((JSONObject) ((JSONObject) ((JSONArray) ((JSONObject) jsonParser
                            .parse(response.getBody()))
                            .get("documents")).get(0)).get("address")).get("y"));
        } catch (Exception ex) {
            ex.printStackTrace();
            x = 126.843553;
            y = 37.5504011;
        }
        ERole role = Utils.getRole();
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("child", c);
        model.addAttribute("adult", a);
        model.addAttribute("x", x);
        model.addAttribute("y", y);
        model.addAttribute("name", role.equals(ERole.ROLE_USER) ? Utils.getName() : "");
        switch (role) {
            case ROLE_USER:
            default:
                return "map";
        }
    }

    @GetMapping(value = "search")
    public String search(Model model, @RequestParam(value = "k", defaultValue = "") String place,
            @RequestParam(value = "s", defaultValue = "") String s,
            @RequestParam(value = "e", defaultValue = "") String e,
            @RequestParam(value = "r", defaultValue = "1") Integer room,
            @RequestParam(value = "a", defaultValue = "0") Integer adult,
            @RequestParam(value = "c", defaultValue = "0") Integer child) throws URISyntaxException, ParseException {
        ERole role = Utils.getRole();
        LocalDate startDate, endDate;
        try {
            startDate = LocalDate.parse(s);
        } catch (Exception err) {
            startDate = LocalDate.now();
        }
        try {
            endDate = LocalDate.parse(s);
            endDate = endDate.plusDays(1);
        } catch (Exception err) {
            endDate = LocalDate.now();
        }
        List<Hotel> hotels = hotelService.findHotels(place);
        model.addAttribute("hotels", hotels);
        model.addAttribute("place", place);
        model.addAttribute("startDate", startDate.toString());
        model.addAttribute("endDate", endDate.toString());
        model.addAttribute("room", room);
        model.addAttribute("adult", adult);
        model.addAttribute("child", child);
        model.addAttribute("name", role.equals(ERole.ROLE_USER) ? Utils.getName() : "");
        switch (role) {
            case ROLE_USER:
            default:
                return "search";
        }
    }

    @GetMapping(value = "/hotel-detail/{pid}")
    public String detail(Model model, @PathVariable("pid") Long pid,
            @RequestParam(value = "s", defaultValue = "") String s,
            @RequestParam(value = "e", defaultValue = "") String e,
            @RequestParam(value = "r", defaultValue = "1") Integer room,
            @RequestParam(value = "a", defaultValue = "0") Integer adult,
            @RequestParam(value = "c", defaultValue = "0") Integer child) {
        ERole role = Utils.getRole();
        LocalDate startDate, endDate;
        try {
            startDate = LocalDate.parse(s);
        } catch (Exception err) {
            startDate = LocalDate.now();
        }
        try {
            endDate = LocalDate.parse(s);
            endDate = endDate.plusDays(1);
        } catch (Exception err) {
            endDate = LocalDate.now();
        }
        Hotel hotel = hotelService.findByPid(pid);
        List<String> photos = new ArrayList();
        photos.add(hotel.getUrl1());
        photos.add(hotel.getUrl2());
        photos.add(hotel.getUrl3());
        photos.add(hotel.getUrl4());
        photos.add(hotel.getUrl5());
        photos.add(hotel.getUrl6());
        photos.add(hotel.getUrl7());
        photos.add(hotel.getUrl8());
        photos.add(hotel.getUrl9());
        photos.add(hotel.getUrl10());
        photos = photos.stream().filter(p -> !p.equals("")).toList();
        List<ReviewDto> reviews = hotelService.getReviews(hotel);
        model.addAttribute("rate1", hotelService.getRatingCount(hotel, 1));
        model.addAttribute("rate2", hotelService.getRatingCount(hotel, 2));
        model.addAttribute("rate3", hotelService.getRatingCount(hotel, 3));
        model.addAttribute("rate4", hotelService.getRatingCount(hotel, 4));
        model.addAttribute("rate5", hotelService.getRatingCount(hotel, 5));
        model.addAttribute("rate1_",
                reviews.size() == 0 ? 0 : (double) hotelService.getRatingCount(hotel, 1) / reviews.size() * 100);
        model.addAttribute("rate2_",
                reviews.size() == 0 ? 0 : (double) hotelService.getRatingCount(hotel, 2) / reviews.size() * 100);
        model.addAttribute("rate3_",
                reviews.size() == 0 ? 0 : (double) hotelService.getRatingCount(hotel, 3) / reviews.size() * 100);
        model.addAttribute("rate4_",
                reviews.size() == 0 ? 0 : (double) hotelService.getRatingCount(hotel, 4) / reviews.size() * 100);
        model.addAttribute("rate5_",
                reviews.size() == 0 ? 0 : (double) hotelService.getRatingCount(hotel, 5) / reviews.size() * 100);
        model.addAttribute("hotel", HotelDto.toDto(hotel));
        model.addAttribute("photos", photos);
        model.addAttribute("reviews", reviews);
        model.addAttribute("name", role.equals(ERole.ROLE_USER) ? Utils.getName() : "");
        model.addAttribute("startDate", startDate.toString());
        model.addAttribute("endDate", endDate.toString());
        model.addAttribute("room", room);
        model.addAttribute("adult", adult);
        model.addAttribute("child", child);
        switch (role) {
            case ROLE_USER:
            default:
                return "detail";
        }
    }

    @GetMapping(value = "/cart")
    public String cart(Model model) {
        ERole role = Utils.getRole();
        List<CartDto> carts = hotelService.getCart(Utils.getPid());
        int price = carts.stream().map(c -> c.getPrice()).mapToInt(Integer::intValue).sum();
        model.addAttribute("carts", carts);
        model.addAttribute("price", price);
        model.addAttribute("name", role.equals(ERole.ROLE_USER) ? Utils.getName() : "");
        switch (role) {
            case ROLE_USER:
                return "cart";
            default:
                return "/";
        }
    }

    @GetMapping(value = "/reserve")
    public String reserve(Model model) {
        ERole role = Utils.getRole();
        model.addAttribute("name", role.equals(ERole.ROLE_USER) ? Utils.getName() : "");
        switch (role) {
            case ROLE_USER:
            default:
                return "reserve";
        }
    }

    @GetMapping("/success")
    public String success(@RequestParam("paymentKey") String paymentKey, @RequestParam("amount") Long amount,
            @RequestParam("orderId") String orderId) throws URISyntaxException, ParseException {
        try {
            RestTemplate restTemplate = new RestTemplate();

            String uri = "https://api.tosspayments.com/v1/payments/confirm";

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic dGVzdF9za19PQUxuUXZEZDJWSjdLTnB2QkRick1qN1g0MW1OOg==");
            headers.add("Content-Type", "application/json");

            Map<String, Object> map = new HashMap<>();
            map.put("amount", amount);
            map.put("orderId", orderId);
            map.put("paymentKey", paymentKey);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

            String result = restTemplate.postForObject(uri, entity, String.class);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            Payment payment = new Payment((String) jsonObject.get("lastTransactionKey"),
                    (String) jsonObject.get("paymentKey"), (String) jsonObject.get("requestedAt"),
                    (String) jsonObject.get("approvedAt"), amount, (String) jsonObject.get("orderId"),
                    (String) jsonObject.get("orderName"), (String) jsonObject.get("method"),
                    (String) ((JSONObject) jsonObject.get("receipt")).get("url"), false,
                    userRepository.findById(Utils.getPid()).orElseThrow());
            paymentRepository.save(payment);
            // cartRepository.deleteByUser(userRepository.findById(Utils.getPid()).orElseThrow());
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/index";
        }
        return "success";
    }

    @PostMapping("/failed")
    public String failed() {
        return "failed";
    }
}