/*
 * TokenExpiryInfoProvider
 */
package com.bcgdv.jwt.providers;

import com.bcgdv.jwt.models.Token;
import com.bcgdv.jwt.models.TokenExpiryInfo;
import javax.inject.Provider;

import static com.bcgdv.jwt.util.PropUtil.getEnvOrProperty;

/**
 * Provides TokenExpiryInfo f
 */
public class TokenExpiryInfoProvider implements Provider<TokenExpiryInfo> {

    /**
     * Provides TokenExpiryInfo
     * @return as TokenExpiryInfo
     */
    @Override
    public TokenExpiryInfo get() {
        return new TokenExpiryInfo(
                convertToMillis(getEnvOrProperty(Token.Type.CLIENT.expiry())),
                convertToMillis(getEnvOrProperty(Token.Type.SESSION.expiry())),
                convertToMillis(getEnvOrProperty(Token.Type.SERVER.expiry())));
    }

    /**
     * Convert to Millis
     * @param tokenExpirySeconds the seconds
     * @return the millis as int
     */
    protected int convertToMillis(String tokenExpirySeconds) {
        return Integer.parseInt(tokenExpirySeconds) * 1000;
    }
}
