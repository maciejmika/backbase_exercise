package utils;

import io.restassured.response.Response;

public class ResponseVerifier {
    public static void assertResponseCode(Response response, Integer expectedCode) {
        if (response.statusCode() != expectedCode) {
            String exceptionMessage = "Response status code = " + response.statusCode() + ", expected code = " + expectedCode.toString() + ", response body: " +
                    ", \n\n" + response.getBody().asPrettyString();
            throw new RuntimeException(exceptionMessage);
        }
    }

    public static void assertContentType(Response response, String contentType) {
        if (!response.getContentType().equals(contentType)) {
            String exceptionMessage = "Content type = " + response.getContentType() + ", expected content type = " + contentType;
            throw new RuntimeException(exceptionMessage);
        }
    }
}
