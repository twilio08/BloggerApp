package com.blog.controller;

import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.payload.JWTAuthResponse;
import com.blog.payload.LoginDto;
import com.blog.payload.SignUpDto;
import com.blog.repository.RoleRepository;
import com.blog.repository.UserRepository;
import com.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        if (userRepo.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email already exists:" + signUpDto.getEmail(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (userRepo.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username already exists:" + signUpDto.getUsername(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role role = roleRepo.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(role));

        userRepo.save(user);
        return new ResponseEntity<>("User Register Successfully", HttpStatus.OK);

    }

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }
}

