package com.bcgdv.jwt.models;

/**
 * We can validate these Token Types
 * CLIENT for Tokens that use an embedded KycSecret
 * SERVER for Tokens that use an embedded ServerSecret.
 * SESSION for Tokens that use an embedded SessionSecret.
 * NONE for Tokens that do not contain a secret, but are still valid Jwt
 */
public enum TokenType {
    CLIENT("CLIENT"),
    SESSION("SESSION"),
    SERVER("SERVER"),
    NONE("NONE");

    TokenType(String name) {
        this.name = name;
    }

    protected String name;

    @Override
    public String toString() {
        return this.name;
    }
}