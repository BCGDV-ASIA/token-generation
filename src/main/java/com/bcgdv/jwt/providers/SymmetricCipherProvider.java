package com.bcgdv.jwt.providers;

import com.google.common.base.Preconditions;
import com.google.inject.Provider;
import com.simonmittag.cryptoutils.symmetric.SimpleSymmetricCipher;
import com.simonmittag.cryptoutils.symmetric.SymmetricKeyAESCipher;

import static com.bcgdv.jwt.Keys.INIT_VECTOR;
import static com.bcgdv.jwt.Keys.SYMMETRIC_SECRET_KEY;
import static com.simonmittag.cryptoutils.PropertyHelper.getEnvOrProperty;


/**
 * Provider class for symmetric cipher creates
 * the shared secret cipher from system property, base 64 encoded string key
 */
public class SymmetricCipherProvider implements Provider<SimpleSymmetricCipher> {

    /**
     * Error messages
     */
    protected static final String SYMMETRIC_KEY_ERROR = "Symmetric key should be set as environment variable or system property: " + SYMMETRIC_SECRET_KEY.toString();
    protected static final String INIT_VECTOR_ERROR = "Init vector key should be set as environment variable or system property: " + INIT_VECTOR.toString();

    /**
     * Build symmetric cipher from System properties, initialize and return.
     * @return as @SimpleSymmetricCipher
     */
    @Override
    public SimpleSymmetricCipher get() {
        SymmetricKeyAESCipher symmetricKeyAESCipher = new SymmetricKeyAESCipher();
        symmetricKeyAESCipher.setKey(Preconditions.checkNotNull(getEnvOrProperty(SYMMETRIC_SECRET_KEY.toString()), SYMMETRIC_KEY_ERROR));
        symmetricKeyAESCipher.setInitVector(Preconditions.checkNotNull(getEnvOrProperty(INIT_VECTOR.toString()), INIT_VECTOR_ERROR));
        return symmetricKeyAESCipher;
    }
}
