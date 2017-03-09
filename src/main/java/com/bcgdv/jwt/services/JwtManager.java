package com.bcgdv.jwt.services;

import com.bcgdv.jwt.models.AsymmetricKeyInfo;
import com.bcgdv.jwt.models.BaseJwtToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.PrivateKey;
import java.util.Base64;

import static com.simonmittag.cryptoutils.asymmetric.KeyHelper.deserializePrivateKey;
import static com.simonmittag.cryptoutils.asymmetric.KeyHelper.deserializePublicKey;

/**
 * Generates JWT Tokens with config found in @AsymmetricKeyInfo and uses Jackson
 * to format JSON input/output
 */
@Singleton
public class JwtManager {
    protected static final Logger logger = LoggerFactory.getLogger(JwtManager.class);

    /**
     * Has key info for signing and verifying
     */
    protected AsymmetricKeyInfo asymmetricKeyInfo;

    /**
     * Has @ObjectMapper for JSON work
     */
    protected ObjectMapper objectMapper;

    /**
     * Build using Guice with keys
     * @param asymmetricKeyInfo as @AsymmetricKeyInfo
     */
    @Inject
    public JwtManager(AsymmetricKeyInfo asymmetricKeyInfo) {
        this.asymmetricKeyInfo = asymmetricKeyInfo;
        this.objectMapper = new ObjectMapper();
    }


    /**
     * Generate a token
     * @param t token subtype
     * @param <T> token subtype
     * @return as T
     */
    public <T extends BaseJwtToken> String generateJwtToken(T t) {
        try {
            Preconditions.checkNotNull(t, "Cannot generate token when payload is null");
            logger.debug("Generating Jwt DefaultToken for object: {}", t);
            PrivateKey privateKey = deserializePrivateKey(asymmetricKeyInfo.getPrivateKey());
            return Jwts.builder().setPayload(objectMapper.writeValueAsString(t))
                    .signWith(SignatureAlgorithm.RS256,
                            privateKey).compact();
        } catch (GeneralSecurityException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Extract payload from an existing encoded token
     * @param token the encoded token
     * @param payloadClass the subtype
     * @param <T> the subtype
     * @return The decoded token as instance
     */
    public <T extends BaseJwtToken> T extractJwtPayload(String token, Class<T> payloadClass) {
        try {
            Key publicKey = deserializePublicKey(asymmetricKeyInfo.getPublickey());
            logger.debug("Extracting jwt payload for given token {}", token);
            String decodedToken = Jwts.parser().setSigningKey(publicKey).
                    parse(token, new StringJwtClaimsHandlerAdapter());
            return objectMapper.readValue(decodedToken, payloadClass);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Split token into header, body and signature
     * @param token the encoded token
     * @return the payload only
     */
    public String extractJwtTokenPayload(String token) {
        Preconditions.checkArgument(token.split("\\.").length == 3, "Invalid JWt token , JWt token shoudl have header,claims and signature");
        String payload = token.split("\\.")[1];
        return new String(Base64.getDecoder().decode(payload));
    }
}
