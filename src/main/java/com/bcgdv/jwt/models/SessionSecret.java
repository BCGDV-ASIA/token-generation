package com.bcgdv.jwt.models;

import java.io.Serializable;
import java.util.Map;

/**
 * Encapsulates the session secret that is encrypted by the generation service.
 */
public class SessionSecret extends Secret implements Serializable {

    /**
     * Pass secret into constructor as Map. Must contain context for token validation
     * @param assertions a Map containing context variable;
     */
    public SessionSecret(Map<String, String> assertions) {
        super(assertions);
        this.tokenType= Token.Type.SESSION;
    }
}
