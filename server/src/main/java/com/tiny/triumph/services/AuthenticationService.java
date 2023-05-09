package com.tiny.triumph.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiny.triumph.dto.AuthenticationRequest;
import com.tiny.triumph.dto.RegistrationRequest;
import com.tiny.triumph.enums.Role;
import com.tiny.triumph.enums.TokenType;
import com.tiny.triumph.exceptions.ResourceNotFoundException;
import com.tiny.triumph.exceptions.UserConstraintsViolationsException;
import com.tiny.triumph.response.AuthenticationResponse;
import com.tiny.triumph.security.token.Token;
import com.tiny.triumph.security.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Set;

@Service
public class AuthenticationService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegistrationRequest requestDTO){


        String encodedPassword = bCryptPasswordEncoder.encode(requestDTO.password());
        System.out.println("Encoded password " + encodedPassword);
        com.tiny.triumph.model.User user = new com.tiny.triumph.model.User(
                requestDTO.firstName().isBlank() ? "" : requestDTO.firstName(),
                requestDTO.lastName().isBlank() ? "" : requestDTO.lastName(),
                requestDTO.email().isBlank() ? "" : requestDTO.email(),
                requestDTO.password().isBlank() ? "" : encodedPassword,
                Role.USER
        );

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()){
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<com.tiny.triumph.model.User>> violations = validator.validate(user);

            if(violations.size() > 0){
                throw new UserConstraintsViolationsException(violations);
            }
        } catch (UserConstraintsViolationsException e) {
            throw new RuntimeException(e);
        }


        userServiceImpl.save(user);

        var savedUser = userServiceImpl.findByEmail(requestDTO.email())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(user, jwtToken);
        return new AuthenticationResponse(jwtToken, refreshToken);
    }
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws AuthenticationException, ResourceNotFoundException {
        System.out.println("Auth email" + request.getEmail().trim());
        System.out.println("Auth password" + request.getPassword().trim());
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch(AuthenticationException exception){
            throw new RuntimeException(exception);
        }

        var user = userServiceImpl.findByEmail(request.getEmail())
                .orElseThrow(()-> new ResourceNotFoundException("A user with the email " + request.getEmail() + " was not found"));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return new AuthenticationResponse(jwtToken, refreshToken);
    }
    @Transactional
    private void saveUserToken(com.tiny.triumph.model.User user, String jwtToken) {
        var token = new Token();
        token.setUser(user);
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);

        tokenRepository.save(token);
    }
    @Transactional
    private void revokeAllUserTokens(com.tiny.triumph.model.User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = userServiceImpl.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = new AuthenticationResponse(accessToken, refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
