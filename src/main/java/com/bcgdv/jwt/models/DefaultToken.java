package com.bcgdv.jwt.models;

/**
 * Token wrapper class for serialisation
 */
public class DefaultToken extends BaseJwtToken {

    /**
     * The encrypted secret, including assertions
     */
    protected String secret;

    /**
     * Not used but needed
     */
    public DefaultToken() {
    }

    /**
     * Create using this constructor.
     * @param secret the encrypted secret.
     * @param expiryTimeInMillis the expiry time.
     */
    public DefaultToken(String secret, Long expiryTimeInMillis) {
        this.secret = secret;
        this.setExpiryInMilliSeconds(expiryTimeInMillis);
    }

    /**
     * The encrypted secret
     * @return as String (URL friendly)
     */
    public String getSecret() {
        return secret;
    }
}
