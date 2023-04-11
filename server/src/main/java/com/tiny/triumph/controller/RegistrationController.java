package com.tiny.triumph.controller;


import com.tiny.triumph.dto.RegistrationRequestDTO;
import com.tiny.triumph.dto.RegistrationResponseDTO;
import com.tiny.triumph.model.User;
import com.tiny.triumph.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class RegistrationController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    PasswordEncoder passwordEncoder;

    public RegistrationController(UserServiceImpl userServiceImpl, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping(value="/register")
    public ResponseEntity<RegistrationResponseDTO> performLogin(@Valid @RequestBody RegistrationRequestDTO registrationRequestDTO){
        User user = new User();
        user.setFirstName(registrationRequestDTO.getFirstName());
        user.setLastName(registrationRequestDTO.getLastName());
        user.setEmail(registrationRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequestDTO.getPassword()));
        userServiceImpl.save(user);
        return ResponseEntity.ok(new RegistrationResponseDTO("Success!"));
    }
}
