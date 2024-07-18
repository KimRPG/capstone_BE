package com.capstonexjapan.line_backend.shop.user.controller;

import com.capstonexjapan.line_backend.shop.user.controller.request.CreateUserDTO;
import com.capstonexjapan.line_backend.shop.user.entity.UserEntity;
import com.capstonexjapan.line_backend.shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public String userCreate(@RequestBody CreateUserDTO userDTO) {
        userService.createUser(userDTO);
        return "유저 생성";
    }

    @GetMapping("")
    public ResponseEntity<?> userCreate(@RequestParam(required = false) Long id) {
        if (id != null) {
            return ResponseEntity.ok(userService.getUser(id));
        }
        return ResponseEntity.ok(userService.getAllUser());
    }

}
