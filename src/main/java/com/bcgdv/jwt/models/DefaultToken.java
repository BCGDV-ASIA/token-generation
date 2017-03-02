package com.bcgdv.jwt.models;

/**
 * Token wrapper class for serialisation
 */
public class DefaultToken extends BaseJwtToken {

    /**
     * The encrypted secret, including assertions
     */
    protected String encryptedSecret;

    /**
     * Not used but needed
     */
    public DefaultToken() {
    }

    /**
     * Create using this constructor.
     * @param encryptedSecret the encrypted secret.
     * @param expiryTimeInMillis the expiry time.
     */
    public DefaultToken(String encryptedSecret, Long expiryTimeInMillis) {
        this.encryptedSecret = encryptedSecret;
        this.setExpiryInMilliSeconds(expiryTimeInMillis);
    }

    /**
     * The encrypted secret
     * @return as String (URL friendly)
     */
    public String getEncryptedSecret() {
        return encryptedSecret;
    }
}
