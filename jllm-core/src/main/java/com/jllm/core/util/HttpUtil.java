package com.jllm.core.util;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * HttpUtil provides utility methods for making HTTP requests.
 * <p>
 * This class simplifies the process of making HTTP requests to external APIs
 * and handles common tasks such as setting headers, sending data, and parsing responses.
 * </p>
 *
 * @author @Noro
 * @version 1.0
 */
public class HttpUtil {

    private static final ObjectMapper  objectMapper = new ObjectMapper();

    /**
     * Makes a GET request to the specified URL.
     *
     * @param url The URL to make the GET request to.
     * @return The response body as a String.
     * @throws IOException if an I/O error occurs.
     */
    public static String get(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        return handleResponse(connection);
    }

    /**
     * Makes a POST request to the specified URL with a JSON body.
     *
     * @param url The URL to make the POST request to.
     * @param payload The JSON payload to send in the request body.
     * @return The response body as a String.
     * @throws IOException if an I/O error occurs.
     */
    public static String post(String url, Object payload) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = objectMapper.writeValueAsBytes(payload);
            os.write(input, 0, input.length);
        }

        return handleResponse(connection);
    }

    /**
     * Handles the HTTP response and converts it into a String.
     *
     * @param connection The connection from which to read the response.
     * @return The response body as a String.
     * @throws IOException if an I/O error occurs.
     */
    private static String handleResponse(HttpURLConnection connection) throws IOException {
        int status = connection.getResponseCode();
        if (status >= 200 && status < 300) {
            return new String(connection.getInputStream().readAllBytes());
        } else {
            throw new IOException("HTTP request failed with status code: " + status);
        }
    }
}
