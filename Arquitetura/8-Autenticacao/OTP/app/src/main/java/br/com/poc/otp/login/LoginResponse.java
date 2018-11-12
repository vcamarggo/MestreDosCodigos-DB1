package br.com.poc.otp.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinicius.camargo on 03/10/2018
 */
public class LoginResponse {
    @SerializedName("id")
    private long id;
    @SerializedName("nome")
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
