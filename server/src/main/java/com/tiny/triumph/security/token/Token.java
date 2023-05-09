package com.tiny.triumph.security.token;

import com.tiny.triumph.enums.TokenType;
import com.tiny.triumph.model.User;
import jakarta.persistence.*;

import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "token")
    public class Token {

        @Id
        @GeneratedValue
        public Integer id;

        @Column(unique = true)
        @NotEmpty
        public String token;

        @Enumerated(EnumType.STRING)
        @Column(name ="token_type")
        public TokenType tokenType = TokenType.BEARER;

        public boolean revoked;

        public boolean expired;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        public User user;

        public Token() {
        }

        public Token(Integer id, String token, TokenType tokenType, boolean revoked, boolean expired, User user) {
            this.id = id;
            this.token = token;
            this.tokenType = tokenType;
            this.revoked = revoked;
            this.expired = expired;
            this.user = user;
        }

        public boolean isExpired() {
            return this.expired;
        }

        public boolean isRevoked() {
            return this.revoked;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public TokenType getTokenType() {
            return tokenType;
        }

        public void setTokenType(TokenType tokenType) {
            this.tokenType = tokenType;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setRevoked(boolean revoked) {
            this.revoked = revoked;
        }

        public void setExpired(boolean expired) {
            this.expired = expired;
        }
    }
