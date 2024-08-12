package com.capstonexjapan.line_backend.shop.user.controller;

import com.capstonexjapan.line_backend.shop.user.controller.request.AddressDTO;
import com.capstonexjapan.line_backend.shop.user.controller.request.CreateUserDTO;
import com.capstonexjapan.line_backend.shop.user.controller.response.GetAddressDTO;
import com.capstonexjapan.line_backend.shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> getUser(@RequestParam(required = false) Long id) {
        if (id != null) {
            return ResponseEntity.ok(userService.getUser(id));
        }
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PostMapping("/address")
    public String userAddressAdd(@RequestBody AddressDTO dto, @RequestParam Long userId) {
        userService.createAddress(dto, userId);
        return "유저에 address 정보 추가";
    }

    @GetMapping("/address")
    public List<GetAddressDTO> getUserAddress(@RequestParam(value = "id") Long userId) {
        return userService.getAddress(userId);
    }


}
