package utils;

import com.github.javafaker.Faker;
import dto.User;
import dto.UserData;
import dto.UserDataBuilder;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import requests.Users;

import static utils.ResponseVerifier.assertResponseCode;

public class UserCreator {
    static public User generateRandomUserData() {
        Faker faker = new Faker();
        UserData userData = UserDataBuilder.builder()
                .email(RandomStringUtils.randomAlphabetic(5).toLowerCase() + "@o2.pl")
                .username(faker.name().firstName() + RandomStringUtils.randomAlphabetic(5))
                .password(faker.internet().password())
                .build();
        return new User(userData);
    }

    public User createRandomUser() {

        User userToRegister = generateRandomUserData();
        Response createdUserResponse = Users.registerUser(userToRegister, RequestSpecs.setupDefaultRequestSpecification());
        assertResponseCode(createdUserResponse, 200);
        User testUser = createdUserResponse.getBody().as(User.class);

        // create complete testUser with password
        return new User(UserDataBuilder.builder()
                .email(testUser.user().email())
                .username(testUser.user().username())
                .password(userToRegister.user().password())
                .token(testUser.user().token())
                .build());
    }
}
