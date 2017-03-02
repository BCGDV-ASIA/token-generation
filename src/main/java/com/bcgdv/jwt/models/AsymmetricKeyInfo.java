package com.bcgdv.jwt.models;

import java.io.Serializable;

/**
 * Wrapper object for RSA 2048 bit keypair, as provided by cryptoutils.
 */
public final class AsymmetricKeyInfo implements Serializable {

    /**
     * private key
     */
    private final String publickey;

    /**
     * public key
     */
    private final String privateKey;

    /**
     * Pass in keypair
     * @param publickey the public key
     * @param privateKey the private key
     */
    public AsymmetricKeyInfo(final String publickey, final String privateKey) {
        this.privateKey = privateKey;
        this.publickey = publickey;
    }

    /**
     * Get the public key
     * @return as String
     */
    public String getPublickey() {
        return publickey;
    }

    /**
     * Get the private key
     * @return as String
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * Never print this
     * @return scrambled.
     */
    @Override
    public String toString() {
        return "";
    }
}
