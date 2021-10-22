package apiTests;

import dto.Article;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import requests.Articles;
import utils.ArticleCreator;
import utils.RequestSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.ResponseVerifier.assertResponseCode;

@Execution(ExecutionMode.CONCURRENT)
public class ArticleTests extends BaseRestAssured {

    @Test
    @DisplayName("Article functionality flow - create article, verify it's correctly posted and then delete it")
    public void articleFlow() {
        Article createdArticle = new ArticleCreator().createRandomArticle(defaultTestUser.user().token());

        // request created article and compare with data used for creation
        Response getArticleResponse = Articles.getArticle(createdArticle.article().slug(), defaultReqSpec);
        assertResponseCode(getArticleResponse, 200);
        Article requestedArticle = getArticleResponse.getBody().as(Article.class);
        assertEquals(createdArticle, requestedArticle);

        //delete article and verify it cannot be found anymore
        Response deleteArticleResponse = Articles.deleteArticle(createdArticle.article().slug(), defaultReqSpec);
        assertResponseCode(deleteArticleResponse, 204);
        Response getDeletedArticleResponse = Articles.getArticle(createdArticle.article().slug(), defaultReqSpec);
        assertResponseCode(getDeletedArticleResponse, 404);
    }

    @Test
    @DisplayName("Given article created by another user, when trying to delete it, then we should get 403 forbidden response")
    public void GivenArticleWhenDeletedByAnotherUserThen403() {
        // Given - create article
        Article createdArticle = new ArticleCreator().createRandomArticle(defaultTestUser.user().token());

        RequestSpecification reqSpecTestUser2 = RequestSpecs.setupDefaultRequestSpecification();
        reqSpecTestUser2.header("jwtauthorization", "Token " + testUser2.user().token());
        // When - try to delete the article by another user
        Response deleteArticleResponse = Articles.deleteArticle(createdArticle.article().slug(), reqSpecTestUser2);
        // Then - verify it's not permitted
        assertResponseCode(deleteArticleResponse, 403);
    }
}