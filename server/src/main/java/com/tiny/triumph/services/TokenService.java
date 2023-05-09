package com.tiny.triumph.services;

import com.tiny.triumph.security.token.Token;
import com.tiny.triumph.security.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {
    @Autowired
    TokenRepository tokenRepository;

    public List<Token> findAll(){
        return tokenRepository.findAll();
    }
}
