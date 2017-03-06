/*
 * Token
 */
package com.bcgdv.jwt.models;

/**
 * @author Simon Mittag
 */
public class Token {

    /**
     * Token expiry in seconds (default)
     */
    public static int DEFAULT_TOKEN_EXPIRY_SECONDS = 60;

    /**
     * Token property for date created
     */
    public static final String DATE_CREATED = "dateCreated";

    /**
     * Token property for expiry in milliseconds
     */
    public static final String EXPIRY_TIME_IN_MILLS_FIELD = "expiryInMilliSeconds";

    /**
     * Token property for secret
     */
    public static final String SECRET = "secret";

    /**
     * We can validate these Token Types. (duplicated on purpose from token-generation to avoid dep)
     * CLIENT for Tokens that use an embedded client secret
     * SERVER for Tokens that use an embedded server secret.
     * SESSION for Tokens that use an embedded, short lived session secret.
     * NONE for Tokens that do not contain a secret, but are still valid Jwt
     */
    public static enum Type {
        CLIENT("CLIENT"),
        SESSION("SESSION"),
        SERVER("SERVER"),
        NONE("NONE");

        Type(String name) {
            this.name = name;
        }

        protected String name;

        @Override
        public String toString() {
            return this.name;
        }
    }
}
