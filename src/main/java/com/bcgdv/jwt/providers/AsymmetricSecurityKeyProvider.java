package com.bcgdv.jwt.providers;

import com.bcgdv.jwt.models.AsymmetricKeyInfo;
import com.google.common.base.Preconditions;
import com.google.inject.Provider;

import javax.inject.Inject;

import static com.bcgdv.jwt.Keys.ASYMMETRIC_PRIVATE_KEY;
import static com.bcgdv.jwt.Keys.ASYMMETRIC_PUBLIC_KEY;
import static com.bcgdv.jwt.util.PropUtil.getEnvOrProperty;

/**
 * Provider implementaiton for @AsymmetricKeyInfo. Does check system properties for values.
 */
public class AsymmetricSecurityKeyProvider implements Provider<AsymmetricKeyInfo> {

    /**
     * Error messages
     */
    protected static final String PRIV_KEY_ERROR = "Private key should be configured as environment variable or property: " + ASYMMETRIC_PRIVATE_KEY.toString();
    protected static final String PUB_KEY_ERROR = "Public key should be configured as environment variable or property: " + ASYMMETRIC_PUBLIC_KEY.toString();

    /**
     * Needed for Guice
     */
    @Inject
    public AsymmetricSecurityKeyProvider() {

    }

    /**
     * Build @AsymmetricKeyInfo and return.
     * @return as @AsymmetricKeyInfo
     */
    @Override
    public AsymmetricKeyInfo get() {
        String privateKey = Preconditions.checkNotNull(getEnvOrProperty(ASYMMETRIC_PRIVATE_KEY.toString()), PRIV_KEY_ERROR).trim();
        String publicKey = Preconditions.checkNotNull(getEnvOrProperty(ASYMMETRIC_PUBLIC_KEY.toString()), PUB_KEY_ERROR).trim();
        return new AsymmetricKeyInfo(publicKey, privateKey);
    }
}
