package br.com.poc.otp.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {

    private static Gson gsonInstance;

    private JsonUtils() {
    }

    public static Gson getGsonInstance() {
        if (gsonInstance == null) {
            gsonInstance = new GsonBuilder()
                    .setExclusionStrategies(new JsonExcludeStrategy())
                    .setLenient()
                    .create();

        }
        return gsonInstance;
    }

}
