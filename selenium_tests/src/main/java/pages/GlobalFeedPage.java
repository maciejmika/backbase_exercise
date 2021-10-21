package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GlobalFeedPage extends PageObject {

    public GlobalFeedPage(WebDriver driver) {
        super(driver);
    }

    public WebElement waitUntilTitleHeaderIsVisible(String articleTitle) {
        String articleTitleLinkXpath = "//a[@class = 'preview-link']/h1[text() = '" + articleTitle + "']";
        waitUntilElementIsVisible(By.xpath(articleTitleLinkXpath));
        return driver.findElement(By.xpath(articleTitleLinkXpath));
    }

    public void clickArticleTitle(String articleTitle) {
        waitUntilTitleHeaderIsVisible(articleTitle).click();
    }

}
