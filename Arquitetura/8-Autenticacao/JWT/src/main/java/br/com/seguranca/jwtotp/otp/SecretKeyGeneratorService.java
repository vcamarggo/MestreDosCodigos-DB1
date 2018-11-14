package br.com.seguranca.jwtotp.otp;

import javax.crypto.SecretKey;

interface SecretKeyGeneratorService {

    String generateSecret();

    SecretKey recoverKey(String secret);
}
