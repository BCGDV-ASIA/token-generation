/**
 * Params
 */
package com.bcgdv.jwt;

import java.util.EnumSet;

/**
 * CLI Params
 */
public enum Params {

    /**
     * ENV
     */
    ENV("ENV"),

    /**
     * CONTEXT
     */
    CONTEXT("CONTEXT"),

    /**
     * TYPE
     */
    TYPE("TYPE");

    /**
     * Valid params
     */
    public static final EnumSet<Params> valid = EnumSet.of(ENV, CONTEXT, TYPE);

    /**
     * has a name
     */
    protected String name;

    /**
     * Build with name
     * @param name as String
     */
    Params(String name) {
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
