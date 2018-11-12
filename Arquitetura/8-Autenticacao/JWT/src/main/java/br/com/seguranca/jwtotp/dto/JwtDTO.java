package br.com.seguranca.jwtotp.dto;

public class JwtDTO {
    private final String authorization;

    public JwtDTO(String authorization) {
        this.authorization = authorization;
    }
}
