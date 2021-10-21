import dto.Article;
import io.github.bonigarcia.seljup.Options;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import pages.ArticleDetailsPage;
import pages.ArticlePage;
import pages.GlobalFeedPage;
import pages.PageObject;
import utils.ArticleCreator;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArticlesTests extends BaseSeleniumTests {

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
    @DisplayName("When all the article fields are filled correctly and article is submitted, then it's present in app and visible in global feed")
    public void WhenArticleDataFilledAndSubmittedThenArticlePresent(WebDriver driver) {
        PageObject pageObject = new PageObject(driver);
        ArticleDetailsPage artDetailsPage = new ArticleDetailsPage(driver);
        ArticlePage articlePage = new ArticlePage(driver);
        GlobalFeedPage globalFeedPage = new GlobalFeedPage(driver);

        // When - create article
        pageObject.openAppAndSignIn(defaultTestUser);
        pageObject.clickNewArticleLink();
        Article testArticle = ArticleCreator.generateRandomArticleData();
        artDetailsPage.enterArticleData(testArticle);
        artDetailsPage.clickPublishArticleButton();

        // Then - verify article was correctly created and is visible in global feed
        articlePage.waitUntilTitleHeaderIsVisible(testArticle.article().title());
        assertThat(driver.getCurrentUrl(), containsString(testArticle.article().title().toLowerCase(Locale.ROOT)));
        pageObject.clickHomeLink();
        pageObject.clickGlobalFeedLink();
        globalFeedPage.waitUntilTitleHeaderIsVisible(testArticle.article().title());
    }

    @TestTemplate
    @DisplayName("Given an article, when we edit it's fields, then changes should be properly displayed in article")
    public void GivenArticleWhenEditedThenChangesProperlyDisplayed(WebDriver driver) {
        PageObject pageObject = new PageObject(driver);
        ArticlePage articlePage = new ArticlePage(driver);
        GlobalFeedPage globalFeedPage = new GlobalFeedPage(driver);
        ArticleDetailsPage artDetailsPage = new ArticleDetailsPage(driver);

        // Given - prepare article
        pageObject.openAppAndSignIn(defaultTestUser);
        Article newArticle = new ArticleCreator().createRandomArticle(defaultTestUser.user().token());
        pageObject.clickGlobalFeedLink();
        globalFeedPage.clickArticleTitle(newArticle.article().title());

        // When - edit article
        articlePage.clickEditArticleButton();
        Article editedArticle = ArticleCreator.generateRandomArticleData();
        artDetailsPage.enterArticleData(editedArticle);
        artDetailsPage.clickPublishArticleButton();

        // Then - verify changes took place and are properly displayed
        articlePage.waitUntilTitleHeaderIsVisible(editedArticle.article().title());
        assertEquals(articlePage.getArticleBody(), editedArticle.article().body());
    }
}
