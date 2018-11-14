package br.com.poc.otp.otp;

/**
 * Created by vinicius.camargo on 03/10/2018
 */
public class SeedBody {
    private final String account;
    private final String seed;

    SeedBody(String account, String seed) {
        this.account = account;
        this.seed = seed;
    }
}

