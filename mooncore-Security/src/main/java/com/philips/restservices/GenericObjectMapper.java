package com.philips.restservices;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


public class GenericObjectMapper {


    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;


    public static <T> T readJSONToJavaObject(String jsonValue, Class<T> typeClass) {

        try {
            return OBJECT_MAPPER.readValue(jsonValue, typeClass);

        } catch (Exception e) {
            throw new RuntimeException("Object Mapping Exception,JSON reponse was empty");

        }
    }


    public static <T> T readJSONToJavaObjectasList(String jsonValue, TypeReference<List<T>> typeReference) {

        try {
            return OBJECT_MAPPER.readValue(jsonValue, typeReference);

        } catch (Exception e) {
            throw new RuntimeException("Object Mapping Exception,JSON reponse was empty");

        }
    }
}
