package br.com.seguranca.jwtotp.otp;


import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.SECONDS;

public class TokenGenerator {

    private final SecretKeyGeneratorService secretKeyGenerator;

    public TokenGenerator(long seed, String... args) {
        this.secretKeyGenerator = new SecretKeyGeneratorService() {
            @Override
            public String generateSecret() {
                return encryptFile(seed, args);
            }

            @Override
            public SecretKey recoverKey(String secret) {
                return new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "AES");
            }
        };
    }

    private static String shuffleString(Long seed, String... args) {
        String arg = String.join("", args);
        String saltedArg = arg.concat("(m^!`7z=d,8a[PxNv=$]{[#;WExb%geW[<bKk25Y]rZ,ALXLmce-su%$?(}cv@w:%EDdG`w=L@`@_FULst[ra&snQ-S5Ny-K9+=dC97VMu[,+%<vg'NwXy3NWmw;]f.<=RW@C9aBv}YJa@ED[JzEH`>?h?,6:uB$=62j.pP>.GDN:]>w}-9y9v6FJeN%B7vaq(?rm.*{Mnum>9Vs&at%XY>Uj@>PQ<*R:Nv9DnC4{Kg>F#'ZTQG4]/t#,^pK@jE.s[%n)p!.6'c:'FZWvu*gp!6[Sf//Wh%cxsj}.@~3hV%^tUYtD)M-[b`+dXUYPM'!xh=/q9@VRWv9(/f*;sC<Uaa5mZDv}eVW?=<V&*t_c$.J=BhTttY_<W?yU&VtpSg/gjX*KzWjfmax*]J@~5Wab,Fu2)Eh9V/pW}=qTPP'*@Fg)mc)CSjrYf/j`AELYQ'FX]B:!?dFDG)4<%,c6g3bGvD]BC*3a**{Z2rk<Y4SVXk9(P9a#2xvQD8^cxz943Lo").substring(0, 512);
        List<String> letters = new ArrayList<>();
        Stream.of(saltedArg.toCharArray()).forEach(chars -> letters.addAll(Stream.of(saltedArg.split("(?!^)")).collect(Collectors.toList())));
        Collections.shuffle(letters, new Random(seed));
        return String.join("", letters);
    }

    private String encryptFile(Long seed, String... args) {
        return Base32String.encode(shuffleString(seed, args).getBytes());
    }

    public Token generate() throws NoSuchAlgorithmException, InvalidKeyException {
        long DURATION = 36;
        TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator(DURATION, SECONDS, 6);

        SecretKey secretKey = secretKeyGenerator.recoverKey(secretKeyGenerator.generateSecret());

        Instant instant = Instant.now(Clock.system(ZoneId.of("America/Sao_Paulo")));
        Date now = new Date(instant.toEpochMilli());
        Date later = new Date(now.getTime() + SECONDS.toMillis(DURATION));

        String currentToken = totp.generateOneTimePassword(secretKey, now);
        String nextToken = totp.generateOneTimePassword(secretKey, later);

        return new Token(currentToken, nextToken);
    }
}
