package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.MediaType;
import rest.ManagerController;

import java.util.Date;
import java.util.Objects;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by vinicius.camargo on 03/07/2018
 */
public class Manager extends ResourceSupport {

    private static final String DEPRECATION_TIME = "None";
    private static final String EN_US = "en_US";

    public int id;
    public String name;
    public String email;
    public Date birthdate;

    @JsonCreator
    public Manager(@JsonProperty("id") int id,
                   @JsonProperty("name") String name,
                   @JsonProperty("email") String email,
                   @JsonProperty("birthdate") Date birthdate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
        if (!this.hasLinks()) {
            //Link de relacao gerente com ele mesmo
            this.add((linkTo(methodOn(ManagerController.class).getManager(this.name))
                    .withRel(Link.REL_SELF)
                    .withMedia(MediaType.APPLICATION_JSON_VALUE)
                    .withType(Manager.class.getSimpleName())
                    .withHreflang(EN_US)
                    .withTitle("Account Manager")
                    .withDeprecation(DEPRECATION_TIME)));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return id == manager.id &&
                Objects.equals(name, manager.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
