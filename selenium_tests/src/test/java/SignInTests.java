import dto.User;
import io.github.bonigarcia.seljup.Options;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import pages.PageObject;
import pages.SignInPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static utils.PropertiesLoader.appProperties;
import static utils.UserCreator.generateRandomUserData;

public class SignInTests extends BaseSeleniumTests {

    @RegisterExtension
    static SeleniumJupiter seleniumJupiter = new SeleniumJupiter();

    @Options
    ChromeOptions chromeOptions = new ChromeOptions();

    {
        chromeOptions.setHeadless(headless);
    }

    @Options
    FirefoxOptions firefoxOptions = new FirefoxOptions();

    {
        firefoxOptions.setHeadless(headless);
        // sorry, Boni Garcia seems to have a little bug here, because although headless chrome works,
        // firefox tends to open in default mode :) but I kept it to show it's a possibility.
    }

    @TestTemplate
    @DisplayName("Given registered user, when we fill email and password fields and click sign in, " +
            "we should be taken to main page and see user name in top bar")
    public void GivenRegisteredUserWhenSignInThenUserLogged(WebDriver driver) {
        PageObject pageObject = new PageObject(driver);
        SignInPage signInPage = new SignInPage(driver);

        //When - sign in
        pageObject.openApp();
        pageObject.clickSignInLink();
        assertFalse(signInPage.signInButton.isEnabled());
        signInPage.enterEmail(defaultTestUser.user().email());
        signInPage.enterPassword(defaultTestUser.user().password());
        signInPage.clickSignInButton();

        // Then - verify user is logged
        pageObject.assertSignInSucceeded(defaultTestUser.user().username());
        assertEquals(appProperties.getPropertyValue("baseUri"), driver.getCurrentUrl());
    }

    @TestTemplate
    @DisplayName("Given non-existent user, when we fill email and password fields and click sign in, " +
            "we should see error about wrong email or password")
    public void GivenUnknownUserWhenSigningInThenError(WebDriver driver) {
        PageObject pageObject = new PageObject(driver);
        SignInPage signInPage = new SignInPage(driver);
        // Given - create non-registered user data
        User unknownUser = generateRandomUserData();

        // When - try to sign in
        pageObject.openApp();
        pageObject.clickSignInLink();
        signInPage.enterEmail(unknownUser.user().email());
        signInPage.enterPassword(unknownUser.user().password());
        signInPage.clickSignInButton();

        //Then - verify if error about wrong credentials is displayed
        assertEquals("email or password is invalid", signInPage.getLoginErrorMessage());
    }
}
