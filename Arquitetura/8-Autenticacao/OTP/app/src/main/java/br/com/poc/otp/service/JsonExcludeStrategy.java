package br.com.poc.otp.service;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class JsonExcludeStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(JsonExclude.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

}
