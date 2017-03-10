/*
 * Secret
 */
package com.bcgdv.jwt.models;

import com.google.common.base.MoreObjects;

import java.util.Map;

import static com.bcgdv.jwt.Params.CONTEXT;

/**
 * Base class for generic encapsulated JWT secrets
 */
public abstract class Secret {


    /**
     * Has a token type
     */
    protected Token.Type tokenType;


    /**
     * Has a map of assertions, as Strings for JSON safety
     */
    protected Map<String, String> assertions;


    /**
     * Don't use this
     */
    protected Secret() {
        //don't call me directly
    }


    /**
     * Pass assertions into Secret.
     * @param assertions a map of assertions
     */
    public Secret(Map<String, String> assertions) {
        if (!assertions.containsKey(CONTEXT.toString())) {
            throw new IllegalArgumentException("secret must provide validation context");
        }
        this.assertions = assertions;
    }


    /**
     * Get the assertions
     * @return as Map
     */
    public Map<String, String> getAssertions() {
        return assertions;
    }


    /**
     * Get the token's context. Used for callbacks.
     * @return as String
     */
    public String getContext() {
        return assertions.get(CONTEXT.toString());
    }


    /**
     * Get the token type
     * @return as TokenType enum
     */
    public Token.Type getTokenType() {
        return this.tokenType;
    }


    /**
     * Print me with type and assertions
     * @return as String
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add(Token.Fields.tokenType.toString(), tokenType)
                .add(Token.Fields.assertions.toString(), getAssertions())
                .toString();
    }
}
