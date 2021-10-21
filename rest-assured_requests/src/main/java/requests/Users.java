package requests;

import dto.User;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.PropertiesLoader;

import static io.restassured.RestAssured.given;

public class Users {

    public static Response registerUser(User user, RequestSpecification reqSpec) {
        Response response = given()
                .spec(reqSpec)
                .body(user)
                .when()
                .post(PropertiesLoader.appProperties.getPropertyValue("registerEndpoint"));
        return response;
    }
}
