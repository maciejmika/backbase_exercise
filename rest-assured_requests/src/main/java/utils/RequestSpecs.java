package utils;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import static utils.PropertiesLoader.appProperties;

public class RequestSpecs {

    public static RequestSpecification setupDefaultRequestSpecification() {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(appProperties.getPropertyValue("baseUri"));
        requestSpecification.contentType("application/json");
        requestSpecification.auth().basic(appProperties.getPropertyValue("username"), appProperties.getPropertyValue("password"));
        return requestSpecification;
    }

    public static RequestSpecification setupDefaultRequestSpecification(String token) {
        RequestSpecification requestSpecification = setupDefaultRequestSpecification();
        requestSpecification.header("jwtauthorization", "Token " + token);
        return requestSpecification;
    }
}
