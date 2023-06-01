package com.tiny.triumph.controller;

import com.tiny.triumph.dto.AuthenticationRequest;
import com.tiny.triumph.dto.RegistrationRequest;
import com.tiny.triumph.exceptions.ResourceNotFoundException;
import com.tiny.triumph.exceptions.UserConstraintsViolationsException;
import com.tiny.triumph.response.AuthenticationResponse;
import com.tiny.triumph.services.AuthenticationService;
import com.tiny.triumph.services.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController extends ResponseEntityExceptionHandler {
    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    public AuthenticationService authenticationService;

    @ExceptionHandler(value = { UserConstraintsViolationsException.class } )
    @PostMapping(value="/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegistrationRequest registrationRequestDTO) throws DataIntegrityViolationException, UserConstraintsViolationsException {

        AuthenticationResponse authenticationResponse = authenticationService.register(registrationRequestDTO);


        boolean wasUserSaved = userServiceImpl.findByEmail(registrationRequestDTO.email()).isPresent();


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authenticationResponse);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest authenticationRequest) throws ResourceNotFoundException {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validate(){
        System.out.println();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Jwt is valid");
    }

}

