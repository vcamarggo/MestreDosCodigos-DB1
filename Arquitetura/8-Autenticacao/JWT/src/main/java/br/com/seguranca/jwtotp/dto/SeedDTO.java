package br.com.seguranca.jwtotp.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class SeedDTO {

    @Id
    private String account;
    private String seed;

    public SeedDTO() {
    }

    public SeedDTO(String account, String seed) {
        this.account = account;
        this.seed = seed;
    }

    public String getAccount() {
        return account;
    }

    public String getSeed() {
        return seed;
    }
}
