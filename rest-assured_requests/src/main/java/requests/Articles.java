package requests;

import dto.Article;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.PropertiesLoader;

import static io.restassured.RestAssured.given;

public class Articles {

    RequestSpecification reqSpec;

    public static Response createArticle(Article article, RequestSpecification reqSpec) {
        Response response = given()
                .spec(reqSpec)
                .body(article)
                .when()
                .post(PropertiesLoader.appProperties.getPropertyValue("articleEndpoint"));
        return response;
    }

    public static Response getArticle(String articleSlug, RequestSpecification reqSpec) {
        Response response = given()
                .spec(reqSpec)
                .when()
                .get(PropertiesLoader.appProperties.getPropertyValue("articleEndpoint") + "/" + articleSlug);
        return response;
    }

    public static Response deleteArticle(String articleSlug, RequestSpecification reqSpec) {
        Response response = given()
                .spec(reqSpec)
                .when()
                .delete(PropertiesLoader.appProperties.getPropertyValue("articleEndpoint") + "/" + articleSlug);
        return response;
    }
}
