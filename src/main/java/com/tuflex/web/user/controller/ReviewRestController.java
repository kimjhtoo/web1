package com.tuflex.web.user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tuflex.web.tool.Utils;
import com.tuflex.web.user.model.ERole;
import com.tuflex.web.user.model.Hotel;
import com.tuflex.web.user.model.Review;
import com.tuflex.web.user.model.Role;
import com.tuflex.web.user.model.User;
import com.tuflex.web.user.payload.request.ReviewRequest;
import com.tuflex.web.user.payload.request.UserRegisterRequest;
import com.tuflex.web.user.repository.ReviewRepository;
import com.tuflex.web.user.repository.RoleRepository;
import com.tuflex.web.user.repository.UserRepository;
import com.tuflex.web.user.service.HotelService;
import com.google.common.base.Predicate;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/review")
public class ReviewRestController {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRegistry sessionRegistry;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    HotelService hotelService;

    // @GetMapping("withdrawal")
    // @PreAuthorize("hasRole('ADMIN')")
    // public ResponseEntity<?> withdrawal(Authentication authentication,
    // @RequestParam Long pid) {
    // Long myPid = Utils.getPid();
    // if (pid == myPid) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("본인을 탈퇴시킬 수
    // 없습니다.");
    // }
    // blockUserService.blockUser(adminRepository.findById(pid).get().getPhone());
    // adminRepository.withdrawal(pid);
    // return ResponseEntity.ok().build();
    // }

    @Transactional
    @RequestMapping(value = "/register.do", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
    public ResponseEntity<?> authenticateUser(@RequestBody ReviewRequest req) throws Exception {
        Long uid = Utils.getPid();
        User user = userRepository.findById(uid).orElseThrow();
        Hotel hotel = hotelService.findByPid(req.getHid());
        Optional<Review> tmp = reviewRepository.findByUserAndHotel(user, hotel);
        if (tmp.isPresent()) {
            Review review = tmp.get();
            review.setContent(req.getContent());
            review.setRate(req.getRating());
            reviewRepository.save(review);
        } else {
            reviewRepository.save(new Review(req.getRating(), req.getContent(), user, hotel));
        }
        return ResponseEntity.ok().build();
    }
}
