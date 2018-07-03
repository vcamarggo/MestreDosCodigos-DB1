package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.MediaType;
import src.AccountController;
import src.ManagerController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by vinicius.camargo on 02/07/2018
 */
public class Account extends ResourceSupport {


    private static final String DEPRECATION_TIME = "None";
    private static final String EN_US = "en-US";

    public Manager manager;
    public int id;
    public double amount;

    //Criador do JSON e seus links das relacoes, tal como especificado no modelo HATEOAS
    @JsonCreator
    public Account(@JsonProperty("id") int id, @JsonProperty("amount") double amount, @JsonProperty("manager") Manager manager) {
        this.id = id;
        this.amount = amount;
        this.manager = manager;
        if (!this.hasLinks()) {
            //Link de relacao de conta com ela mesma
            this.add((linkTo(methodOn(AccountController.class).getAccount(this.id))
                    .withSelfRel()
                    .withMedia(MediaType.APPLICATION_JSON_VALUE)
                    .withType(Account.class.getSimpleName())
                    .withHreflang(EN_US)
                    .withTitle("Bank Account")
                    .withDeprecation(DEPRECATION_TIME)));
            //Link de relacao de conta com seu gerente
            this.add((linkTo(methodOn(ManagerController.class).getManager(this.manager.name))
                    .withRel("manager")
                    .withMedia(MediaType.APPLICATION_JSON_VALUE)
                    .withType(Manager.class.getSimpleName())
                    .withHreflang(EN_US)
                    .withTitle("Account Manager")
                    .withDeprecation(DEPRECATION_TIME)));
        }
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
