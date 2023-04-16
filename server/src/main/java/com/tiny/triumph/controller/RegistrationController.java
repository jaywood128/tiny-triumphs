package com.tiny.triumph.controller;


import com.tiny.triumph.dto.RegistrationRequestDTO;
import com.tiny.triumph.dto.RegistrationResponseDTO;
import com.tiny.triumph.exceptions.UserConstraintsViolationsException;
import com.tiny.triumph.model.User;
import com.tiny.triumph.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.*;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class RegistrationController extends ResponseEntityExceptionHandler {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @ExceptionHandler(value = { UserConstraintsViolationsException.class } )
    @PostMapping(value="/register")
    public ResponseEntity<RegistrationResponseDTO> registerUser(@RequestBody RegistrationRequestDTO registrationRequestDTO) throws DataIntegrityViolationException{
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory();){
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<RegistrationRequestDTO>> violations = validator.validate(registrationRequestDTO);

            if(violations.size() > 0){
                throw new UserConstraintsViolationsException(violations);
            }
        }
        User user = new User(
                registrationRequestDTO.getFirstName().isBlank() ? "" : registrationRequestDTO.getFirstName(),
                registrationRequestDTO.getLastName().isBlank() ? "" : registrationRequestDTO.getLastName(),
                registrationRequestDTO.getEmail().isBlank() ? "" : registrationRequestDTO.getEmail(),
                registrationRequestDTO.getPassword().isBlank() ? "" : bCryptPasswordEncoder.encode(registrationRequestDTO.getPassword())
        );

        userServiceImpl.save(user);
        return ResponseEntity.ok(new RegistrationResponseDTO("Success!"));
    }
}
