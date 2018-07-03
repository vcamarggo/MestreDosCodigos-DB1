package src.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by vinicius.camargo on 29/06/2018
 */
class JsonUtils {

    private static Gson gsonInstance;

    private JsonUtils() {
    }

    // metodo que utiliza Singleton para prover a aplicacao um conversor de Json para Objetos (POJO)
    static Gson getGsonInstance() {
        if (gsonInstance == null) {
            gsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return gsonInstance;
    }

}
