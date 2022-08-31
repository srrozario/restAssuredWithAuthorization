package test;

import baseTest.BaseTest;
import com.thedeanda.lorem.LoremIpsum;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeleteLastArticle extends BaseTest {
    @Test
    public void deleteLastArticleWithValidToken() {
        int lastArticleId = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + getBearerToken())
                .log().headers()
                .log().uri()
                .when()
                .get("/articles")
                .then()
                .statusCode(200)
                .log().body()
                .extract().jsonPath().getInt("result[-1].id");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + getBearerToken())
                .log().headers()
                .log().uri()
                .when()
                .delete("/article/delete/" +lastArticleId)
                .then()
                .statusCode(200)
                .log().body()
                .body("message", equalTo("Article has been deleted"))
                .body("success", equalTo(true));
    }

    @Test
    public void deleteLastArticleWithInvalidToken() {
        int lastArticleId = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + getBearerToken())
                .log().headers()
                .log().uri()
                .when()
                .get("/articles")
                .then()
                .statusCode(200)
                .log().body()
                .extract().jsonPath().getInt("result[-1].id");

        String token = LoremIpsum.getInstance().getWords(5,8);
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .log().headers()
                .log().uri()
                .when()
                .delete("/article/delete/" +lastArticleId)
                .then()
                .statusCode(404)
                .log().body()
                .body("message", equalTo("API not found or wrong method"));
    }
}
