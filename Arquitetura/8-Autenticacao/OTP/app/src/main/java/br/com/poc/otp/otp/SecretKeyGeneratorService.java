package br.com.poc.otp.otp;

import java.io.UnsupportedEncodingException;

import javax.crypto.SecretKey;

interface SecretKeyGeneratorService {

    String generateSecret();

    SecretKey recoverKey(String secret) throws UnsupportedEncodingException;
}
