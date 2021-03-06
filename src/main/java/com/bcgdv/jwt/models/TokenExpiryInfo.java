package com.bcgdv.jwt.models;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * Set Token Expiry by @Token.Type
 */
public final class TokenExpiryInfo implements Serializable {
    protected Integer sessionTokenExpiryInMillis;
    protected Integer serverTokenExpiryInMillis;
    protected Integer clientTokenExpiryInMillis;

    /**
     * Defaults to never expire, but don't use this in production.
     */
    public TokenExpiryInfo() {
        this.sessionTokenExpiryInMillis = Token.EXPIRY_NEVER;
        this.serverTokenExpiryInMillis = Token.EXPIRY_NEVER;
        this.clientTokenExpiryInMillis = Token.EXPIRY_NEVER;
    }

    /**
     * Pass in expiry values as Integers instead
     * @param clientTokenExpiryInMillis as Integer
     * @param sessionTokenExpiryInMillis as Integer
     * @param serverTokenExpiryInMillis as Integer
     */
    public TokenExpiryInfo(final Integer clientTokenExpiryInMillis,
                           final Integer sessionTokenExpiryInMillis,
                           final Integer serverTokenExpiryInMillis) {
        this();
        this.sessionTokenExpiryInMillis = sessionTokenExpiryInMillis;
        this.serverTokenExpiryInMillis = serverTokenExpiryInMillis;
        this.clientTokenExpiryInMillis = clientTokenExpiryInMillis;
    }

    /**
     * Get session token expiry time in milliseconds
     * @return as Integer
     */
    public Integer getSessionTokenExpiryInMillis() {
        return sessionTokenExpiryInMillis;
    }


    /**
     * Get server token expiry time in milliseconds
     * @return as Integer
     */
    public Integer getServerTokenExpiryInMillis() {
        return serverTokenExpiryInMillis;
    }

    /**
     * Get client token expiry time in milliseconds
     * @return as Integer
     */
    public Integer getClientTokenExpiryInMillis() {
        return clientTokenExpiryInMillis;
    }

    /**
     * Get token expiry by @TokenType
     * @param type the tokenType
     * @return as Integer
     */
    public Integer getTokenTypeTimeoutInMillis(Token.Type type) {
        switch(type) {
            case SESSION:
                return getSessionTokenExpiryInMillis();
            case CLIENT:
                return getClientTokenExpiryInMillis();
            case SERVER:
                return getServerTokenExpiryInMillis();
            default:
                throw new IllegalArgumentException("not a valid token type");
        }
    }

    /**
     * Format as JSON
     * @return as JSON String
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sessionTokenExpiryInMillis", sessionTokenExpiryInMillis)
                .add("serverTokenExpiryInMillis", serverTokenExpiryInMillis)
                .add("clientTokenExpiryInMillis", clientTokenExpiryInMillis)
                .toString();
    }
}
