package com.bcgdv.jwt.services;

import com.bcgdv.jwt.models.TokenExpiryInfo;

import java.util.Map;

/**
 * Factory for different token types
 */
public interface TokenGenerationService {

    /**
     * Generate a client token for a validation context and assertions
     * @param assertions the assertions
     * @return the JWT token
     */
    String generateClientToken(Map<String, String> assertions);


    /**
     * Generate a client token for a validation context and assertions
     * @param assertions the assertions
     * @return the JWT token
     */
    String generateSessionToken(Map<String, String> assertions);


    /**
     * Generate a client token for a validation context and assertions
     * @param assertions the assertions
     * @return the JWT token
     */
    String generateServerToken(Map<String, String> assertions);

    /**
     * Fetch the @TokenExpiryInfo
     * @return the TokenExpiryInfo
     */
    TokenExpiryInfo getTokenExpiryInfo();
}
