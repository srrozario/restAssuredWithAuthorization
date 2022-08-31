package test;

import baseTest.BaseTest;
import com.thedeanda.lorem.LoremIpsum;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetArticleDetails extends BaseTest {

    @Test
    public void getArticleDetailWithValidToken(){
        given()
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer " +getBearerToken())
                .log().headers()
                .log().uri()
                .when()
                .get("/articles")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void getArticleDetailWithoutToken(){
        given()
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer")
                .log().headers()
                .log().uri()
                .when()
                .get("/articles")
                .then()
                .statusCode(404)
                .body("message", equalTo("API not found or wrong method"))
                .log().body();
    }

    @Test
    public void getArticleDetailWitInvalidToken(){
        String token = LoremIpsum.getInstance().getWords(5,8);


        given()
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer "+token)
                .log().headers()
                .log().uri()
                .log().body()
                .when()
                .get("/articles")
                .then()
                .statusCode(404)
                .body("message", equalTo("API not found or wrong method"))
                .log().body();
    }
}
