package src.service;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinicius.camargo on 29/06/2018
 * POJO para representar um Repostorio do Github
 */
public class Repository {
    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("url")
    private String url;

    @SerializedName("private")
    private Boolean isPrivate;

    @Override
    public String toString() {
        return "Repository{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
