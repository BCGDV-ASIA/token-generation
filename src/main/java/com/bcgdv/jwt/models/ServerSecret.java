package com.bcgdv.jwt.models;

import java.io.Serializable;
import java.util.Map;

/**
 * Encapsulates the server secret that is encrypted by the generation service.
 */
public class ServerSecret extends Secret implements Serializable {

    /**
     * Pass secret into constructor as Map. Must contain "context" for token validation
     * @param assertions a Map containing assertions as Strings
     */
    public ServerSecret(Map<String, String> assertions) {
        super(assertions);
        this.tokenType= Token.Type.SERVER;
    }
}
