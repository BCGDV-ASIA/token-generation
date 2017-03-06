/*
 * Generator
 */
package com.bcgdv.jwt;

import com.bcgdv.jwt.models.Token;
import com.bcgdv.jwt.models.TokenExpiryInfo;
import com.bcgdv.jwt.providers.AsymmetricSecurityKeyProvider;
import com.bcgdv.jwt.providers.SymmetricCipherProvider;
import com.bcgdv.jwt.services.JwtManager;
import com.bcgdv.jwt.services.TokenGenerationService;
import com.bcgdv.jwt.services.TokenGenerationServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * CLI Generator for JWT Tokens generates tokens of type @TokenType, with an encrypted secret,
 * implemented as a HashMap of assertions, contained within the token. The Token is encrypted
 * with a symmetric cipher that is initialized via @SymmetricCipherProvider and signed with a
 * private key that is initialized via @AsymmetricSecurityKeyProvider.
 */
public class Generator {

    /**
     * Format tokens as JSON, should probably be moved to objectmapper
     */
    protected static final String JSON_TOKEN = "{\"type\":\"%s\", \"context\":\"%s\", \"env\":\"%s\", \"expiry\":\"%s\", \"token\":\"%s\"}";

    /**
     * Has a TokenGenerationService for all JWT work
     */
    protected TokenGenerationService tokenGenerationService;

    /**
     * Has a configuration map
     */
    protected Map<String, String> config;

    /**
     * You can build a generator using a config
     * @param config as Map
     */
    public Generator(Map<String, String> config) {
        this.config = config;
        this.tokenGenerationService = new TokenGenerationServiceImpl(
                new JwtManager(
                        new AsymmetricSecurityKeyProvider().get()
                ),
                new SymmetricCipherProvider().get(),
                new TokenExpiryInfo()
        );
    }

    /**
     * CLI Entry point
     * @param args as Map
     */
    public static void main(String[] args) {
        Map<String, String> config = parseArgs(args);

        Generator generator = new Generator(config);

        switch (Token.Type.valueOf(config.get(Params.TYPE.toString()))) {
            case CLIENT:
                System.out.println(generator.formatClientToken());
                break;
            case SESSION:
                System.out.println(generator.formatSessionToken());
                break;
            case SERVER:
                System.out.println(generator.formatServerToken());
                break;
            default:
                usage();
        }
    }

    /**
     * Format a client token.
     * @return as String
     */
    public String formatClientToken() {
        return String.format(JSON_TOKEN,
                Token.Type.CLIENT.toString(),
                config.get(Params.CONTEXT.toString()),
                config.get(Params.ENV.toString()),
                tokenGenerationService.getTokenExpiryInfo().getClientTokenExpiryInMillis().toString(),
                tokenGenerationService.generateClientToken(config));
    }

    /**
     * Format a Session token
     * @return as JSON String
     */
    public String formatSessionToken() {
        return String.format(JSON_TOKEN,
                Token.Type.SESSION.toString(),
                config.get(Params.CONTEXT.toString()),
                config.get(Params.ENV.toString()),
                tokenGenerationService.getTokenExpiryInfo().getSessionTokenExpiryInMillis().toString(),
                tokenGenerationService.generateSessionToken(config));
    }

    /**
     * Format a Server token
     * @return as JSON String
     */
    public String formatServerToken() {
        return String.format(JSON_TOKEN,
                Token.Type.SERVER.toString(),
                config.get(Params.CONTEXT.toString()),
                config.get(Params.ENV.toString()),
                tokenGenerationService.getTokenExpiryInfo().getServerTokenExpiryInMillis().toString(),
                tokenGenerationService.generateServerToken(config));
    }

    /**
     * Check the CLI args
     * @param args as array
     * @return nothing but throws RuntimeException
     */
    protected static String[] checkArgs(String[] args) {
        if (args.length < 3) {
            usageExit();
        } else {
            try {
                Token.Type.valueOf(args[0].toUpperCase());
            } catch (Exception e) {
                usageExit();
            }
            if (!args[1].startsWith("/")) {
                usageExit();
            }
            Environments env = Environments.valueOf(args[2].toUpperCase());
            System.out.println("");
        }
        return args;
    }

    /**
     * Parse the CLI args
     * @param args as array
     * @return as Map
     */
    protected static Map<String, String> parseArgs(String[] args) {
        Map<String, String> config = new HashMap<>();
        extractType(checkArgs(args), config);
        extractContext(args, config);
        extractEnvironment(args, config);
        return config;
    }

    /**
     * Extract token environment
     * @param args from Array
     * @param config and put it in this Map
     */
    protected static void extractEnvironment(String[] args, Map config) {
        config.put(Params.ENV.toString(), args[2].toUpperCase());
    }

    /**
     * Extract type
     * @param args from Array
     * @param config into Map
     */
    protected static void extractType(String[] args, Map config) {
        config.put(Params.TYPE.toString(), args[0].toUpperCase());
    }

    /**
     * Extract context
     * @param args from Array
     * @param config into Map
     */
    protected static void extractContext(String[] args, Map config) {
        config.put(Params.CONTEXT.toString(), args[1]);
    }

    /**
     * Print usage
     */
    protected static void usage() {
        System.out.println("Usage: Generator ['client' | 'session' | 'server' ] context ['dev' | 'test' | 'prod']");
    }

    /**
     * Print usage and exit
     */
    protected static void usageExit() {
        usage();
        System.exit(-1);
    }
}
