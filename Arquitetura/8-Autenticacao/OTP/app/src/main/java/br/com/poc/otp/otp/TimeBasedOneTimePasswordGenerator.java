package br.com.poc.otp.otp;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.SecretKey;

/**
 * <p>Generates time-based one-time passwords (TOTP) as specified in
 * <a href="https://tools.ietf.org/html/rfc6238">RFC&nbsp;6238</a>.</p>
 *
 * <p>{@code TimeBasedOneTimePasswordGenerator} instances are thread-safe and may be shared and re-used across multiple
 * threads.</p>
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 */
public class TimeBasedOneTimePasswordGenerator extends HmacOneTimePasswordGenerator {
    private final long timeStepMillis;

    /**
     * A string identifier for the HMAC-SHA1 algorithm (required by HOTP and allowed by TOTP). HMAC-SHA1 is the default
     * algorithm for TOTP.
     */
    public static final String TOTP_ALGORITHM_HMAC_SHA1 = "HmacSHA1";

    /**
     * A string identifier for the HMAC-SHA256 algorithm (allowed by TOTP).
     */
    public static final String TOTP_ALGORITHM_HMAC_SHA256 = "HmacSHA256";

    /**
     * A string identifier for the HMAC-SHA512 algorithm (allowed by TOTP).
     */
    public static final String TOTP_ALGORITHM_HMAC_SHA512 = "HmacSHA512";

    /**
     * Constructs a new time-based one-time password generator with a default time-step (30 seconds), password length
     *
     * @throws NoSuchAlgorithmException if the underlying JRE doesn't support the
     * happen except in cases of serious misconfiguration
     */
    public TimeBasedOneTimePasswordGenerator() throws NoSuchAlgorithmException {
        this(30, TimeUnit.SECONDS);
    }

    /**
     * Constructs a new time-based one-time password generator with the given time-step and a default password length
     *
     * @param timeStep the magnitude of the time-step for this generator
     * @param timeStepUnit the units for the the given time step
     *
     * @throws NoSuchAlgorithmException if the underlying JRE doesn't support the
     * happen except in cases of serious misconfiguration
     */
    public TimeBasedOneTimePasswordGenerator(final long timeStep, final TimeUnit timeStepUnit) throws NoSuchAlgorithmException {
        this(timeStep, timeStepUnit, HmacOneTimePasswordGenerator.DEFAULT_PASSWORD_LENGTH);
    }

    /**
     * Constructs a new time-based one-time password generator with the given time-step and password length and a
     *
     * @param timeStep the magnitude of the time-step for this generator
     * @param timeStepUnit the units for the the given time step
     * @param passwordLength the length, in decimal digits, of the one-time passwords to be generated; must be between
     * 6 and 8, inclusive
     *
     * @throws NoSuchAlgorithmException if the underlying JRE doesn't support the
     * happen except in cases of serious misconfiguration
     */
    public TimeBasedOneTimePasswordGenerator(final long timeStep, final TimeUnit timeStepUnit, final int passwordLength) throws NoSuchAlgorithmException {
        this(timeStep, timeStepUnit, passwordLength, TOTP_ALGORITHM_HMAC_SHA256);
    }

    /**
     * Constructs a new time-based one-time password generator with the given time-step, password length, and HMAC
     * algorithm.
     *
     * @param timeStep the magnitude of the time-step for this generator
     * @param timeStepUnit the units for the the given time step
     * @param passwordLength the length, in decimal digits, of the one-time passwords to be generated; must be between
     * 6 and 8, inclusive
     * @param algorithm the name of the {@link javax.crypto.Mac} algorithm to use when generating passwords; TOTP allows
     */
    public TimeBasedOneTimePasswordGenerator(final long timeStep, final TimeUnit timeStepUnit, final int passwordLength, final String algorithm) throws NoSuchAlgorithmException {
        super(passwordLength, algorithm);

        this.timeStepMillis = timeStepUnit.toMillis(timeStep);
    }

    /**
     * Generates a one-time password using the given key and timestamp.
     *
     * @param secretKey a secret key to be used to generate the password
     * @param timestamp the timestamp for which to generate the password
     *
     * @return an integer representation of a one-time password; callers will need to format the password for display
     * on their own
     *
     * @throws InvalidKeyException if the given key is inappropriate for initializing the {@link Mac} for this generator
     */
    public String generateOneTimePassword(final SecretKey secretKey, final Date timestamp) throws InvalidKeyException {
        return this.generateOneTimePassword(secretKey, timestamp.getTime() / this.timeStepMillis);
    }

    /**
     * Returns the time step used by this generator.
     *
     * @param timeUnit the units of time in which to return the time step
     *
     * @return the time step used by this generator in the given units of time
     */
    public long getTimeStep(final TimeUnit timeUnit) {
        return timeUnit.convert(this.timeStepMillis, TimeUnit.MILLISECONDS);
    }
}
