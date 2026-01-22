package com.example.testasync.controller;

import com.example.testasync.dto.request.LoginRequest;
import com.example.testasync.dto.request.UserCreateRequest;
import com.example.testasync.dto.response.LoginResponse;
import com.example.testasync.dto.response.UserResponse;
import com.example.testasync.entity.User;
import com.example.testasync.service.UserService;
import io.jsonwebtoken.lang.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Map;

@SuppressWarnings("NullableProblems")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> signUp(@RequestBody UserCreateRequest userCreateRequest) {
        try {
            return ResponseEntity.ok(userService.signUp(userCreateRequest));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "error", e.getMessage(),
                            "code", HttpStatus.UNAUTHORIZED.value())
                    );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest);
        try {
            return ResponseEntity.ok(userService.login(loginRequest));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "error", e.getMessage(),
                            "code", HttpStatus.UNAUTHORIZED.value())
                    );
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUserDetail(@PathVariable String id) {
        try {
            return ResponseEntity.ok(userService.getUserDetail(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of(
                            "error", e.getMessage(),
                            "code", HttpStatus.UNAUTHORIZED.value()
                    )
            );
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }

}
