package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignInPage extends PageObject {

    @FindBy(xpath = "//input[@formcontrolname = 'email']")
    public WebElement emailInputField;
    @FindBy(xpath = "//input[@formcontrolname = 'password']")
    public WebElement passwordInputField;
    @FindBy(xpath = "//button[@type = 'submit']")
    public WebElement signInButton;
    @FindBy(className = "error-messages")
    public WebElement loginErrorMessage;

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    public void enterEmail(String email) {
        waitUntilElementIsVisible(emailInputField);
        emailInputField.clear();
        emailInputField.sendKeys(email);
    }

    public void enterPassword(String password) {
        waitUntilElementIsVisible(passwordInputField);
        passwordInputField.clear();
        passwordInputField.sendKeys(password);
    }

    public void clickSignInButton() {
        waitUntilElementIsClickable(signInButton);
        signInButton.click();
    }

    public String getLoginErrorMessage() {
        waitUntilElementIsVisible(loginErrorMessage);
        return loginErrorMessage.getText();
    }
}
