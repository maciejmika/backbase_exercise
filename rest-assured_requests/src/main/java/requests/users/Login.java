package requests.users;

import dto.User;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static utils.PropertiesLoader.appProperties;

public class Login {

    public static Response authenticate(User user, RequestSpecification reqSpec) {
        Response response = given()
                .spec(reqSpec)
                .body(user)
                .when()
                .post(appProperties.getPropertyValue("loginEndpoint"));
        return response;
    }
}
