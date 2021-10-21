package pages;

import dto.User;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

import static utils.PropertiesLoader.appProperties;

public class PageObject {
    public static final int TIMEOUT_IN_SECONDS = 20;
    public static final int POLLING_TIME_IN_MILLIS = 200;
    @FindBy(linkText = "Sign in")
    public WebElement signInLink;
    @FindBy(linkText = "New Article")
    public WebElement newArticleLink;
    @FindBy(linkText = "Home")
    public WebElement homeLink;
    @FindBy(linkText = "Global Feed")
    public WebElement globalFeedLink;
    protected WebDriver driver;

    public PageObject(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public <T> T waitUntilConditionMeet(ExpectedCondition<T> condition) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(TIMEOUT_IN_SECONDS))
                .pollingEvery(Duration.ofMillis(POLLING_TIME_IN_MILLIS))
                .ignoring(NoSuchElementException.class)
                .until(condition);
    }

    public void waitUntilElementIsVisible(WebElement element) {
        waitUntilConditionMeet(ExpectedConditions.visibilityOf(element));
    }

    public void waitUntilElementIsVisible(By by) {
        waitUntilConditionMeet(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitUntilElementIsClickable(WebElement element) {
        waitUntilConditionMeet(ExpectedConditions.elementToBeClickable(element));
    }

    public void openApp() {
        driver.get("https://candidatex:qa-is-cool@qa-task.backbasecloud.com");
        driver.get(appProperties.getPropertyValue("baseUri"));
        driver.manage().window().maximize();
    }

    public void openAppAndSignIn(User testUser) {
        openApp();
        clickSignInLink();
        SignInPage signInPage = new SignInPage(driver);
        signInPage.enterEmail(testUser.user().email());
        signInPage.enterPassword(testUser.user().password());
        signInPage.clickSignInButton();
    }


    public void clickSignInLink() {
        waitUntilElementIsClickable(signInLink);
        signInLink.click();
    }

    public void assertSignInSucceeded(String username) {
        waitUntilElementIsVisible(By.xpath("//a[@href = '/profile/" + username + "']"));
    }

    public void clickNewArticleLink() {
        waitUntilElementIsClickable(newArticleLink);
        newArticleLink.click();
    }

    public void clickHomeLink() {
        waitUntilElementIsClickable(homeLink);
        homeLink.click();
    }

    public void clickGlobalFeedLink() {
        waitUntilElementIsClickable(globalFeedLink);
        globalFeedLink.click();
    }
}
