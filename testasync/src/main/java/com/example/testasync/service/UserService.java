package com.example.testasync.service;

import com.example.testasync.dto.request.LoginRequest;
import com.example.testasync.dto.request.UserCreateRequest;
import com.example.testasync.dto.response.LoginResponse;
import com.example.testasync.dto.response.UserResponse;
import com.example.testasync.entity.Role;
import com.example.testasync.entity.User;
import com.example.testasync.repository.RoleRepository;
import com.example.testasync.repository.UserRepository;
import com.example.testasync.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authManager;

    public UserResponse signUp(UserCreateRequest userCreateRequest) throws NoSuchElementException {
        Role role = roleRepository.findByRoleName(userCreateRequest.getRoleName()).orElseThrow();
        String passwordEncode = passwordEncoder.encode(userCreateRequest.getPassword());
        User user = new User();
        user.setName(userCreateRequest.getName());
        user.setAddress(userCreateRequest.getAddress());
        user.setAge(userCreateRequest.getAge());
        user.setUsername(userCreateRequest.getUsername());
        user.setPassword(passwordEncode);
        user.setRoleUser(role);
        User save = userRepository.save(user);
        return new UserResponse(
                save.getUsername(),
                save.getPassword()
        );
    }

    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        System.err.println(password);
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        assert userDetails != null;
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        String token = jwtUtils.generateToken(userDetails, user.getName());
        System.err.println(user.getPassword() + " va " + userDetails.getPassword());
        return new LoginResponse(
                user.getUsername(),
                user.getPassword(),
                token
        );
    }
}
