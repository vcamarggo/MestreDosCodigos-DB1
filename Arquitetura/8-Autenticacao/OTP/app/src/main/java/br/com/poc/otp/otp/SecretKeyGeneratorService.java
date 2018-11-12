package br.com.poc.otp.otp;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;

public interface SecretKeyGeneratorService {

    String generateSecret() throws NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException;

    SecretKey recoverKey(String secret) throws NoSuchAlgorithmException, UnsupportedEncodingException;
}
