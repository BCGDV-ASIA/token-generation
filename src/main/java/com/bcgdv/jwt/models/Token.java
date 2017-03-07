/*
 * Token
 */
package com.bcgdv.jwt.models;

/**
 * Token constants
 */
public class Token {


    /**
     * Number of parts in the token (header, payload, signature)
     */
    public static final int LENGTH = 3;


    /**
     * Token expiry in seconds (default)
     */
    public static int DEFAULT_EXPIRY_SECONDS = 60;


    /**
     * Token expiry in seconds (default)
     */
    public static int EXPIRY_NEVER = -1;

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

        public String expiry() {
            return this.name +   "_TOKEN_EXPIRY";
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    /**
     * @author Simon Mittag
     */
    public static enum Fields {

        dateCreated("dateCreated"),
        expiryInMilliSeconds("expiryInMilliSeconds"),
        secret("secret"),
        tokenType("tokenType"),
        context("context");

        /**
         * has a name
         */
        protected String name;

        /**
         * Build with name
         * @param name as String
         */
        Fields(String name) {
            this.name=name;
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
}
