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

    public static String shuffleString(Long seed, String... args) {
        String arg = Stream.of(args).collect(Collectors.joining());
        String saltedArg = arg.concat("(m^!`7z=d,8a[PxNv=$]{[#;WExb%geW[<bKk25Y]rZ,ALXLmce-su%$?(}cv@w:%EDdG`w=L@`@_FULst[ra&snQ-S5Ny-K9+=dC97VMu[,+%<vg'NwXy3NWmw;]f.<=RW@C9aBv}YJa@ED[JzEH`>?h?,6:uB$=62j.pP>.GDN:]>w}-9y9v6FJeN%B7vaq(?rm.*{Mnum>9Vs&at%XY>Uj@>PQ<*R:Nv9DnC4{Kg>F#'ZTQG4]/t#,^pK@jE.s[%n)p!.6'c:'FZWvu*gp!6[Sf//Wh%cxsj}.@~3hV%^tUYtD)M-[b`+dXUYPM'!xh=/q9@VRWv9(/f*;sC<Uaa5mZDv}eVW?=<V&*t_c$.J=BhTttY_<W?yU&VtpSg/gjX*KzWjfmax*]J@~5Wab,Fu2)Eh9V/pW}=qTPP'*@Fg)mc)CSjrYf/j`AELYQ'FX]B:!?dFDG)4<%,c6g3bGvD]BC*3a**{Z2rk<Y4SVXk9(P9a#2xvQD8^cxz943Lo").substring(0, 512);
        List<String> letters = new ArrayList<>();
        Stream.of(saltedArg.toCharArray()).forEach(chars -> letters.addAll(Stream.of(saltedArg.split("(?!^)")).toList()));
        Collections.shuffle(letters, new Random(seed));
        return Stream.of(letters).collect(Collectors.joining());
    }

    public String encryptFile(Long seed, String... args) {
        return Base32String.encode(shuffleString(seed, args).getBytes());
    }

    public Token generate() throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
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
