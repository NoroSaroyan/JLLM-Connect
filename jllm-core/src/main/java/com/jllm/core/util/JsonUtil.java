package com.jllm.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * JsonUtil provides utility methods for serializing and deserializing JSON data.
 * <p>
 * This class simplifies the process of converting Java objects to JSON and vice versa,
 * using the Jackson library for JSON processing.
 * </p>
 *
 * @author @Noro
 * @version 1.0
 */
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts a Java object to a JSON string.
     *
     * @param object The Java object to convert to JSON.
     * @return The JSON string representation of the object.
     * @throws IOException If an error occurs during conversion.
     */
    public static String toJson(Object object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }

    /**
     * Converts a JSON string to a Java object.
     *
     * @param json      The JSON string to convert.
     * @param valueType The class type to convert the JSON to.
     * @param <T>       The type of the object to return.
     * @return The Java object representation of the JSON string.
     * @throws IOException If an error occurs during conversion.
     */
    public static <T> T fromJson(String json, Class<T> valueType) throws IOException {
        return objectMapper.readValue(json, valueType);
    }
}
