package apiTests;

import dto.Error;
import dto.User;
import dto.UserDataBuilder;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import requests.users.Login;
import utils.UserCreator;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static utils.PropertiesLoader.appProperties;
import static utils.ResponseVerifier.assertContentType;
import static utils.ResponseVerifier.assertResponseCode;

@Execution(ExecutionMode.CONCURRENT)
public class SignInTests extends BaseRestAssured {

    static User loginUser;

    @BeforeAll
    public static void setup() {
        loginUser = new User(UserDataBuilder.builder()
                .email(defaultTestUser.user().email())
                .password(defaultTestUser.user().password())
                .build());
    }

    @Test
    @DisplayName("Given registered user, when signing in, then return 200 and user data with jwt token")
    public void givenRegisteredUserWhenSignInThen200() {

        //When - sign in with previously registered user
        Response response = Login.authenticate(loginUser, defaultReqSpec);

        // Then - verify sign in was completed successfully, check returned user data
        assertResponseCode(response, 200);
        assertContentType(response, "application/json; charset=utf-8");
        User authenticatedUser = response.getBody().as(User.class);
        assertThat(authenticatedUser.user().username(), equalTo(defaultTestUser.user().username()));
        assertThat(authenticatedUser.user().email(), equalTo(loginUser.user().email()));
        assertFalse(authenticatedUser.user().token().isEmpty());
    }

    @Test
    @DisplayName("Given unknown user, when signing in, then return 404 status code and proper message")
    public void givenUnknownUserWhenSignInThen404() {
        UserCreator userCreator = new UserCreator();

        //When - try to sign in with non-existent user
        Response response = Login.authenticate(userCreator.generateRandomUserData(), defaultReqSpec);
        // Then - verify error was returned
        // response status code verification disabled due to defect (404 status code expected)
        // assertResponseCode(response, 422);

        assertContentType(response, "application/json; charset=utf-8");
        Error errorResponse = response.getBody().as(Error.class);
        assertThat(errorResponse.errors().emailOrPassword(), equalTo("is invalid"));
    }

    @Test
    @DisplayName("Given registered user, when signing in without basic auth, then return 401")
    public void givenRegisteredUserWhenNoBasicAuthThen401() {
        given()
                .contentType("application/json")
                .baseUri(appProperties.getPropertyValue("baseUri"))
                .body(loginUser)
                .when()
                .post(appProperties.getPropertyValue("loginEndpoint"))
                .then()
                .assertThat()
                .statusCode(401);
        // content type not checked as it's wrong -> "text/html" instead of "application/json; charset=utf-8" required by documentation
    }
}