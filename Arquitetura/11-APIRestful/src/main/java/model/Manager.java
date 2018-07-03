package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Objects;

/**
 * Created by vinicius.camargo on 03/07/2018
 */
public class Manager {
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
