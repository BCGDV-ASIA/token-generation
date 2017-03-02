/*
 * TokenGenerationTest
 */
package com.bcgdv.jwt;

import com.bcgdv.jwt.models.AsymmetricKeyInfo;
import com.bcgdv.jwt.models.DefaultToken;
import com.bcgdv.jwt.models.TokenExpiryInfo;
import com.bcgdv.jwt.models.TokenType;
import com.bcgdv.jwt.providers.SymmetricCipherProvider;
import com.bcgdv.jwt.services.JwtManager;
import com.bcgdv.jwt.services.TokenGenerationService;
import com.bcgdv.jwt.services.TokenGenerationServiceImpl;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.simonmittag.cryptoutils.symmetric.SimpleSymmetricCipher;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author simonmittag
 * @since <version>
 */
public class TokenGenerationTest {
    protected TokenExpiryInfo tokenExpiryInfo;

    protected JwtManager jwtManager;
    protected SimpleSymmetricCipher symmetricCipher;
    protected TokenGenerationService tokenGenerationService;

    @Before
    public void setUp() {
        System.setProperty("SYMMETRIC_SECRET_KEY", "8888999988889999");
        System.setProperty("INIT_VECTOR", "8888999988889999");
        String privateRSA2048Key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCCRqXPYSvfndzYYwa7C1vb9USwgNJPFqpcjON2P89FlsMentHIim/WBNSSdEBzgF0tgFFhUtea7+DTnxGx4PCJHxrYe4uLcgFp0ANHZ50Jv5xWqryV4LT7D5Maso/qJ1N5Kzvw8A1l+pLT4vBhiI2WXO3DAGqsL/d2SU9EtcPhPTBgIgP6usSkHm6hSEm9Ot7oJsXSU3HIZuKqGD95/rSmvXEtXj6bahOdityss8bV89V6Khxyk8huK4HYGCBLrWhaiPd49A8k+6bT/bg77hz7RpsTWb+65VbHAPDPsKxdvitpPGHmz07AGBmx65J4KyJYXisOdh5QKgxQ7HOKH6LhAgMBAAECggEAY07DetBSB4ZpITJroQ3JmmTfVcT9jHh0qVRk2HFP6XZBiwf6Vurg0b6g5Htl3iYN4W+dmZjy7hLJycfKT1RvR7fEWR3khAoF9ckxlq7vUM0VuiMw2veOPovi0Uko14/e29EPQl1beU+HuamXIGJdvC9et3WL23I9K5g6qt/8T6ekkbonvtz04XqvbCwhR9y+1CT11CaTyHOIexsnxCH9X+ZXuv0he40uhKEIGWxYTbXsS8vI7XQwmiVPLmJyq/4jCtWVHwK9+rmJYaHzBZQ2xm2AACzFqqyts3u+kaK5s5enNUZQ3R2m1brBxLmAruCZWoHLggwswksfIACqzKOMgQKBgQC/Ck/LalHGOxHLDKKsPwH38fVmZclPmS2+3TO9lAt802Sep4i0sS40JqGZyiZpxvl7BRnp+e7C0pCN5/16RcJtZV9wsAWQ7b/vv95aon26TxyfY+sZvD+0qMtVw8Js+g6n1dC02hXdqw+eAjK3L7QHMqh40IRUPslXWa9q6Bw6mQKBgQCukutva3CW3c3CV7NkY0nujSVMzouB1vc3TNktoKkVCDiSZpJLkbaW4oN9JV7wT2uHWP3uME3gsMzDuuKNbzfcLRXub6/7UCEuU08CD6u+WnCvpGvXUviFqCNVBWKqOsGG5EOaa69zGBQ9qhwOTxbr8mD7Lp/bmdFvL/8LyGHfiQKBgAsPO8NY6lcBgGwy1RAHrcN+3GgLKHZVNdLA14f4FBor2r1ckYt0XmpieiR/EW46OKMojnF/KJVav0eX0JLNiw3uOnI5nnjfaqckgmjY01J/1fZcQHYC8eUxiGP0q0LHzyVkOWre3Tkx9/t7wRfdyRxJkHs+Bwshf2JxLLiRq2CxAoGAfaxAls5GmCUGufA+sXcUwwmTH47VOVxSTdohwkoVTpHDgAkdKg+03TwlolLj3KWf6Ci1bOITPjyovALukxjMotFGJ4b5rjaKHHpWt0omvfsHgjogdDtDs5xOCye8VoXDyeLLlAAPpxBfieDLH7Xp72NmU4zGAeXismNf/NB9PNkCgYAGBc5zvWIx9gjNPMY69vuuVLFK8a231NJVIuob8rNbYSiwKHY6dXHA0/xHEB2exf82N9JkiV482X8FpQVcr6wATrzrVbORouf2j4VsIUdCt32FLoBe3hiibQKPlU1nC0tAJfqDm/1K9d9YSUYC9XTQ+t5wWya3j7/P63tZtoHvAA==";
        String publicRSA2048Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgkalz2Er353c2GMGuwtb2/VEsIDSTxaqXIzjdj/PRZbDHp7RyIpv1gTUknRAc4BdLYBRYVLXmu/g058RseDwiR8a2HuLi3IBadADR2edCb+cVqq8leC0+w+TGrKP6idTeSs78PANZfqS0+LwYYiNllztwwBqrC/3dklPRLXD4T0wYCID+rrEpB5uoUhJvTre6CbF0lNxyGbiqhg/ef60pr1xLV4+m2oTnYrcrLPG1fPVeioccpPIbiuB2BggS61oWoj3ePQPJPum0/24O+4c+0abE1m/uuVWxwDwz7CsXb4raTxh5s9OwBgZseuSeCsiWF4rDnYeUCoMUOxzih+i4QIDAQAB";

        tokenExpiryInfo = new TokenExpiryInfo(-1, 5 * 60 * 1000, 5 * 60 * 1000);

        jwtManager =
                new JwtManager(
                        new AsymmetricKeyInfo(
                                publicRSA2048Key,
                                privateRSA2048Key));

        symmetricCipher = new SymmetricCipherProvider().get();

        tokenGenerationService = new TokenGenerationServiceImpl(
                jwtManager,
                symmetricCipher,
                tokenExpiryInfo
        );
    }

    @Test
    public void testClientTokenExists() {
        String clientToken = tokenGenerationService.generateClientToken(
                Maps.newHashMap(
                        ImmutableMap.of(
                                Params.CONTEXT.toString(),
                                "/customer",
                                "customerId",
                                "123456")));
        System.out.println(clientToken);
        assertNotNull(clientToken);
    }

    @Test
    public void testSessionTokenExists() {
        String sessionToken = tokenGenerationService.generateSessionToken(
                Maps.newHashMap(
                        ImmutableMap.of(
                                Params.CONTEXT.toString(),
                                "/customer",
                                "customerId",
                                "123456")));
        System.out.println(sessionToken);
        assertNotNull(sessionToken);
    }

    @Test
    public void testServerTokenExists() {
        String serverToken = tokenGenerationService.generateServerToken(
                Maps.newHashMap(
                        ImmutableMap.of(
                                Params.CONTEXT.toString(),
                                "/customer")));
        System.out.println(serverToken);
        assertNotNull(serverToken);
    }

    @Test
    public void testClientTokenContentsMayBeAsserted() {
        String customerId = "123456";
        String clientToken = tokenGenerationService.generateClientToken(
                Maps.newHashMap(
                        ImmutableMap.of(
                                Params.CONTEXT.toString(),
                                "/customer",
                                "customerId",
                                "123456")));
        DefaultToken token = jwtManager.extractJwtPayload(clientToken, DefaultToken.class);
        assertEquals((long) tokenExpiryInfo.getClientTokenExpiryInMillis(), (long) token.getExpiryInMilliSeconds());
        String contents = symmetricCipher.decrypt(token.getEncryptedSecret());
        assertTrue(contents.contains(customerId));
        assertTrue(contents.contains(TokenType.CLIENT.toString()));
    }

    @Test
    public void testClientTokenContentsMayBeAssertedWithMsIsdn() {
        String customerId = "123456";
        String msIsdn = "+61400212109";
        String clientToken = tokenGenerationService.generateClientToken(
                Maps.newHashMap(
                        ImmutableMap.of(
                                Params.CONTEXT.toString(),
                                "/customer",
                                "customerId",
                                "123456",
                                "msIsdn",
                                msIsdn)));
        DefaultToken token = jwtManager.extractJwtPayload(clientToken, DefaultToken.class);
        assertEquals((long) tokenExpiryInfo.getClientTokenExpiryInMillis(), (long) token.getExpiryInMilliSeconds());
        String contents = symmetricCipher.decrypt(token.getEncryptedSecret());
        assertTrue(contents.contains(customerId));
        assertTrue(contents.contains(msIsdn));
        assertTrue(contents.contains(TokenType.CLIENT.toString()));
    }

    @Test
    public void testSessionTokenContentsMayBeAsserted() {
        String customerId = "123456";
        String sessionToken = tokenGenerationService.generateSessionToken(
                Maps.newHashMap(
                        ImmutableMap.of(
                                Params.CONTEXT.toString(),
                                "/customer",
                                "customerId",
                                "123456")));
        DefaultToken token = jwtManager.extractJwtPayload(sessionToken, DefaultToken.class);
        assertEquals((long) tokenExpiryInfo.getSessionTokenExpiryInMillis(), (long) token.getExpiryInMilliSeconds());
        String contents = symmetricCipher.decrypt(token.getEncryptedSecret());
        assertTrue(contents.contains(customerId));
        assertTrue(contents.contains(TokenType.SESSION.toString()));
    }

    @Test
    public void testSessionTokenContentsMayBeAssertedWithMsIsdn() {
        String customerId = "123456";
        String msIsdn = "+61400212109";
        String sessionToken = tokenGenerationService.generateSessionToken(
                Maps.newHashMap(
                        ImmutableMap.of(
                                Params.CONTEXT.toString(),
                                "/customer",
                                "customerId",
                                "123456",
                                "msIsdn",
                                msIsdn)));
        DefaultToken token = jwtManager.extractJwtPayload(sessionToken, DefaultToken.class);
        assertEquals((long) tokenExpiryInfo.getSessionTokenExpiryInMillis(), (long) token.getExpiryInMilliSeconds());
        String contents = symmetricCipher.decrypt(token.getEncryptedSecret());
        assertTrue(contents.contains(customerId));
        assertTrue(contents.contains(TokenType.SESSION.toString()));
        assertTrue(contents.contains(msIsdn));

    }

    @Test
    public void testServerTokenContentsMayBeAsserted() {
        String context = "/customer";
        String serverToken = tokenGenerationService.generateServerToken(
                Maps.newHashMap(
                        ImmutableMap.of(
                                Params.CONTEXT.toString(),
                                "/customer")));
        DefaultToken token = jwtManager.extractJwtPayload(serverToken, DefaultToken.class);
        assertEquals((long) tokenExpiryInfo.getServerTokenExpiryInMillis(), (long) token.getExpiryInMilliSeconds());
        String contents = symmetricCipher.decrypt(token.getEncryptedSecret());
        assertTrue(contents.contains(context));
        assertTrue(contents.contains(TokenType.SERVER.toString()));
    }
}
