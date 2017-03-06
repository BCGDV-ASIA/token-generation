package com.bcgdv.jwt.services;

import com.bcgdv.jwt.Params;
import com.bcgdv.jwt.models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.simonmittag.cryptoutils.symmetric.SimpleSymmetricCipher;

import java.util.Map;

/**
 * Generates JWT tokens by type.
 */
@Singleton
public class TokenGenerationServiceImpl implements TokenGenerationService {

    /**
     * Has a @SimpleSymmetricCipher to encrypt token payload
     */
    protected SimpleSymmetricCipher simpleSymmetricCipher;

    /**
     * Has a JwtManager to sign, verify and encode tokens
     */
    protected JwtManager jwtManager;

    /**
     * Has a TokenExpiryInfo for timestamps
     */
    protected TokenExpiryInfo tokenExpiryInfo;

    /**
     * Has an ObjectMapper for JSON de/serialisation
     */
    protected ObjectMapper objectMapper;

    /**
     * Needs JwtManager,  SimpleSymmetricCipher and TokenExpiryInfo
     * @param jwtManager the JwtManager
     * @param simpleSymmetricCipher the cipher to encrypt payloads
     * @param tokenExpiryInfo the token expiry info in millis
     */
    @Inject
    public TokenGenerationServiceImpl(JwtManager jwtManager,
                                      SimpleSymmetricCipher simpleSymmetricCipher,
                                      TokenExpiryInfo tokenExpiryInfo) {
        this.jwtManager = jwtManager;
        this.simpleSymmetricCipher = simpleSymmetricCipher;
        this.tokenExpiryInfo = tokenExpiryInfo;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Generate a server token
     * @param assertions the secret
     * @return the JWT server token
     */
    public String generateServerToken(Map<String, String> assertions) {
        return this.generateToken(Token.Type.SERVER, assertions.get(Params.CONTEXT.toString()), assertions);
    }

    /**
     * Generate a session token
     * @param assertions the assertions
     * @return the JWT session token
     */
    public String generateSessionToken(Map<String, String> assertions) {
        return this.generateToken(Token.Type.SESSION, assertions.get(Params.CONTEXT.toString()), assertions);
    }

    /**
     * Generate a client token
     * @param assertions the assertions
     * @return the JWT client token
     */
    public String generateClientToken(Map<String, String> assertions) {
        return this.generateToken(Token.Type.CLIENT, assertions.get(Params.CONTEXT.toString()), assertions);
    }

    /**
     * Fetch the tokenExpiryInfo
     * @return the TokenExpiryInfo
     */
    @Override
    public TokenExpiryInfo getTokenExpiryInfo() {
        return this.tokenExpiryInfo;
    }

    /**
     * Fetch the @SimpleSymmetricCipher
     * @return the SimpleSymmetricCipher
     */
    @Override
    public SimpleSymmetricCipher getSimpleSymmetricCipher() {
        return this.simpleSymmetricCipher;
    }

    /**
     * Fetch the @JwtManager
     * @return the @JwtManager
     */
    @Override
    public JwtManager getJwtManager() {
        return this.jwtManager;
    }

    /**
     * Generates a typed token
     * @param type the token type
     * @param context the validation context
     * @param assertions the token's secret
     * @return the JWT token
     */
    protected String generateToken(Token.Type type, String context, Map<String, String> assertions) {
        Preconditions.checkNotNull(context, "context cannot be null");
        Preconditions.checkNotNull(assertions, "secret cannot be null");
        assertions.put(Params.CONTEXT.toString(), context);
        return jwtManager.generateJwtToken(
                new DefaultToken(
                        convertToJsonAndEncrypt(
                            encapsulateSecret(
                                    type,
                                    assertions
                            )
                        ),
                        tokenExpiryInfo.getTokenTypeTimeoutInMillis(type).longValue()
                )
        );
    }

    /**
     * wrap or encapsulate secret in internal Type
     * @param type the token Type
     * @param assertions the secret
     * @return the typed Secret
     */
    protected Secret encapsulateSecret(Token.Type type, Map<String, String> assertions) {
        switch(type) {
            case SESSION:
                return new SessionSecret(assertions);
            case CLIENT:
                return new ClientSecret(assertions);
            case SERVER:
                return new ServerSecret(assertions);
            default:
                throw new IllegalArgumentException("not a valid token type");
        }
    }


    /**
     * Invoke the symmetric cipher to encrypt secret
     * @param t the secret subtype to encrypt
     * @param <T> the secret subtype to encrypt
     * @return the subtype
     */
    protected <T> String convertToJsonAndEncrypt(T t) {
        try {
            return simpleSymmetricCipher.encrypt(objectMapper.writeValueAsString(t));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
