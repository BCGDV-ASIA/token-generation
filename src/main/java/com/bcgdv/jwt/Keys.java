/**
 * Params
 */
package com.bcgdv.jwt;

/**
 * Key constants for internal use.
 */
public enum Keys {

    /**
     * RSA keypair private key
     */
    ASYMMETRIC_PRIVATE_KEY("ASYMMETRIC_PRIVATE_KEY"),

    /**
     * RSA keypair public key
     */
    ASYMMETRIC_PUBLIC_KEY("ASYMMETRIC_PUBLIC_KEY"),

    /**
     * Symmetric Secret key
     */
    SYMMETRIC_SECRET_KEY("SYMMETRIC_SECRET_KEY"),

    /**
     * Init Vector for secret key
     */
    INIT_VECTOR("INIT_VECTOR");

    /**
     * has a name
     */
    protected String name;

    /**
     * Build with name
     * @param name as String
     */
    Keys(String name) {
        this.name = name;
    }


    /**
     * Print with name
     * @return as String
     */
    @Override
    public String toString() {
        return name();
    }
}
