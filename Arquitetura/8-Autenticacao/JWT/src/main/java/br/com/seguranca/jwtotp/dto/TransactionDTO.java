package br.com.seguranca.jwtotp.dto;

public class TransactionDTO {
    private String id;
    private String account;
    private String otp;

    public String getAccount() {
        return account;
    }

    public String getOtp() {
        return otp;
    }
}
