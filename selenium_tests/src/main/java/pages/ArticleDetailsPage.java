package pages;

import dto.Article;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ArticleDetailsPage extends PageObject {

    @FindBy(xpath = "//input[@formcontrolname = 'title']")
    public WebElement titleInputField;
    @FindBy(xpath = "//input[@formcontrolname = 'description']")
    public WebElement descriptionInputField;
    @FindBy(xpath = "//textarea[@formcontrolname = 'body']")
    public WebElement articleBodyInputField;
    @FindBy(xpath = "//button[contains(text(), 'Publish Article')]")
    public WebElement publishArtButton;

    public ArticleDetailsPage(WebDriver driver) {
        super(driver);
    }

    public void enterArticleData(Article article) {
        enterTitle(article.article().title());
        enterDescription(article.article().description());
        enterArticleBody(article.article().body());
        // no tags as they seem not to be working
    }

    public void enterTitle(String title) {
        waitUntilElementIsVisible(titleInputField);
        titleInputField.clear();
        titleInputField.sendKeys(title);
    }

    public void enterDescription(String desc) {
        waitUntilElementIsVisible(descriptionInputField);
        descriptionInputField.clear();
        descriptionInputField.sendKeys(desc);
    }

    public void enterArticleBody(String body) {
        waitUntilElementIsVisible(articleBodyInputField);
        articleBodyInputField.clear();
        articleBodyInputField.sendKeys(body);
    }

    public void clickPublishArticleButton() {
        waitUntilElementIsClickable(publishArtButton);
        publishArtButton.click();
    }
}
