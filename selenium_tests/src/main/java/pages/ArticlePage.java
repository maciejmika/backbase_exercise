package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ArticlePage extends PageObject {

    @FindBy(xpath = "//div[@class = 'container']//a[contains(text(), 'Edit Article')]")
    WebElement upperEditArticleButton;
    @FindBy(xpath = "//div[contains(@class, 'article-content')]//p")
    WebElement articleBody;

    public ArticlePage(WebDriver driver) {
        super(driver);
    }

    public void waitUntilTitleHeaderIsVisible(String articleTitle) {
        String titleHeaderXpath = "//h1[text() = '" + articleTitle + "']";
        waitUntilElementIsVisible(By.xpath(titleHeaderXpath));
    }

    public void clickEditArticleButton() {
        waitUntilElementIsClickable(upperEditArticleButton);
        upperEditArticleButton.click();
    }

    public String getArticleBody() {
        waitUntilElementIsVisible(articleBody);
        return articleBody.getText();
    }
}
