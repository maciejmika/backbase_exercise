package apiTests;

import com.github.javafaker.Faker;
import dto.User;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import utils.RequestSpecs;
import utils.UserCreator;

public class BaseRestAssured {

    static User defaultTestUser;
    static User testUser2;
    static Faker faker;
    static RequestSpecification defaultReqSpec;

    @BeforeAll
    public static void registerTestUsers() {
        UserCreator userCreator = new UserCreator();
        defaultTestUser = userCreator.createRandomUser();
        testUser2 = userCreator.createRandomUser();
        faker = new Faker();
        defaultReqSpec = RequestSpecs.setupDefaultRequestSpecification(defaultTestUser.user().token());
    }
}
