import dto.User;
import org.junit.jupiter.api.BeforeAll;
import utils.UserCreator;

import static utils.PropertiesLoader.appProperties;

public class BaseSeleniumTests {

    static User defaultTestUser;
    boolean headless = Boolean.parseBoolean(appProperties.getPropertyValue("headless"));

    @BeforeAll
    public static void registerUsers() {
        UserCreator userCreator = new UserCreator();
        defaultTestUser = userCreator.createRandomUser();
    }
}
