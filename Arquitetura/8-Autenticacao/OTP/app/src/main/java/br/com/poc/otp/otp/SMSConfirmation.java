package br.com.poc.otp.otp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinicius.camargo on 03/10/2018
 */
public class SMSConfirmation {
    @SerializedName("idConta")
    private final long id;
    @SerializedName("codigoValidacao")
    private final String smsCode;

    public SMSConfirmation(long id, String smsCode) {
        this.id = id;
        this.smsCode = smsCode;
    }
}
