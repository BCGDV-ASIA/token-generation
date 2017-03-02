/**
 * Params
 */
package com.bcgdv.jwt;

import java.util.EnumSet;

/**
 * JWT Token Environments
 */
public enum Environments {

    /**
     * We support DEV
     */
    DEV("DEV"),

    /**
     * TEST
     */
    TEST("TEST"),

    /**
     * and PROD environments
     */
    PROD("PROD");

    /**
     * If it's not one of these it's not valid
     */
    public static final EnumSet<Environments> valid = EnumSet.of(DEV, TEST, PROD);

    /**
     * Has a name
     */
    protected String name;

    /**
     * Build me like this
     * @param name with a name
     */
    Environments(String name) {
        this.name=name;
    }


    /**
     * Represent me as simple String.
     * @return as String
     */
    @Override
    public String toString() {
        return name();
    }
}
