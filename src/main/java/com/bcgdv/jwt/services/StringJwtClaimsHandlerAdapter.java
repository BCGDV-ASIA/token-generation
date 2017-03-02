package com.bcgdv.jwt.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;

/**
 * Needs doco
 */
public class StringJwtClaimsHandlerAdapter extends JwtHandlerAdapter<String> {

    /**
     * Needs doco
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Needs doco
     * @param claims needs doco
     * @return needs doco
     */
    private String deserializeClaims(Claims claims) {
        try {
            return objectMapper.writeValueAsString(claims);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Needs doco
     * @param jwt needs doco
     * @return needs doco
     */
    @Override
    public String onClaimsJwt(Jwt<Header, Claims> jwt) {
        return deserializeClaims(jwt.getBody());
    }

    /**
     * Needs doco
     * @param jws needs doco
     * @return needs doco.
     */
    @Override
    public String onClaimsJws(Jws<Claims> jws) {
        return deserializeClaims(jws.getBody());
    }
}
