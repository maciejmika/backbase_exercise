package utils;

import com.github.javafaker.Faker;
import dto.Article;
import dto.ArticleDataBuilder;
import io.restassured.response.Response;
import requests.Articles;

import java.util.List;

import static utils.ResponseVerifier.assertResponseCode;

public class ArticleCreator {
    static public Article generateRandomArticleData() {
        Faker faker = new Faker();
        return new Article(ArticleDataBuilder.builder()
                .title(faker.ancient().god())
                .description(faker.lorem().sentence())
                .body(faker.lorem().sentence())
                .tagList(List.of(faker.ancient().hero(), "backbase"))
                .build()
        );
    }

    public Article createRandomArticle(String token) {
        Article newArticle = generateRandomArticleData();
        Response createArticleResponse = Articles.createArticle(newArticle, RequestSpecs.setupDefaultRequestSpecification(token));
        assertResponseCode(createArticleResponse, 200);
        return createArticleResponse.getBody().as(Article.class);
    }
}
