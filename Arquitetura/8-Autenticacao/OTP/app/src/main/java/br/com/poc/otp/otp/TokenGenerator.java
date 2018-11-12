package br.com.poc.otp.otp;


import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import org.threeten.bp.Clock;
import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static java.util.concurrent.TimeUnit.SECONDS;

public class TokenGenerator {

    private final SecretKeyGeneratorService secretKeyGenerator;
    private final long DURATION = 36;

    public TokenGenerator(long seed, String... args) {
        this.secretKeyGenerator = new SecretKeyGeneratorService() {
            @Override
            public String generateSecret() {
                return encryptFile(seed, args);
            }

            @Override
            public SecretKey recoverKey(String secret) throws UnsupportedEncodingException {
                return new SecretKeySpec(secret.getBytes("UTF-8"), "AES");
            }
        };
    }


    public String encryptFile(Long seed, String... args) {
        return Base32String.encode(shuffleStringInServer(seed, args).getBytes());
    }

    public Token generate() throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator(DURATION, SECONDS, 6);

        SecretKey secretKey = secretKeyGenerator.recoverKey(secretKeyGenerator.generateSecret());

        Instant instant = Instant.now(Clock.system(ZoneId.of("America/Sao_Paulo")));
        Date now = new Date(instant.toEpochMilli());
        Date later = new Date(now.getTime() + SECONDS.toMillis(DURATION));

        String currentToken = totp.generateOneTimePassword(secretKey, now);
        String nextToken = totp.generateOneTimePassword(secretKey, later);

        return new Token(currentToken, nextToken);
    }

    public static String shuffleStringInServer(Long seed, String... args) {
        List<String> letters = new ArrayList<>();
        Stream.of(args).forEach(arg -> letters.addAll(Stream.of(arg.split("(?!^)")).toList()));
        Collections.shuffle(letters, new Random(seed));
        return Stream.of(letters).collect(Collectors.joining());
    }
}
