package com.storedemo.librarysystem.Controllers;

import com.storedemo.librarysystem.DTOs.User.CreateUserDTO;
import com.storedemo.librarysystem.DTOs.User.UserDTO;
import com.storedemo.librarysystem.Entities.LoginRequest;
import com.storedemo.librarysystem.Entities.SignUpRequest;
import com.storedemo.librarysystem.Repositories.UserRepository;
import com.storedemo.librarysystem.Services.UserService;
import com.storedemo.librarysystem.Utils.JwtResponse;
import com.storedemo.librarysystem.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            System.out.println(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).toList();

            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getUsername(), roles));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest){
        System.out.println(signUpRequest.getUsername());
        System.out.println(signUpRequest.getPassword());
        System.out.println(signUpRequest.getFirstName());
        System.out.println(signUpRequest.getLastName());
        UserDTO existingUser = userService.getUserByEmail(signUpRequest.getUsername());
            if(existingUser != null){
                return ResponseEntity.badRequest().body("Error: Username is already taken!");
            }
            else {
                CreateUserDTO createUserDTO = new CreateUserDTO(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getUsername(), signUpRequest.getPassword());
                UserDTO userDTO = userService.createUser(createUserDTO);
                return ResponseEntity.ok(userDTO);
            }
    }
}
