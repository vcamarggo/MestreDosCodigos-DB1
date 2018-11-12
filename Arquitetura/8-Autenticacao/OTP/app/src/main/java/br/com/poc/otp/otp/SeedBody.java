package br.com.poc.otp.otp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinicius.camargo on 03/10/2018
 */
public class SeedBody {
    private final String account;
    private final String seed;

    public SeedBody(long id, String seed) {
        this.id = id;
        this.seed = seed;
    }
}
