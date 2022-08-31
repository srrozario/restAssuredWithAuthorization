package test;

import baseTest.BaseTest;
import com.thedeanda.lorem.LoremIpsum;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateArticle extends BaseTest {


    @Test
    public void createArticleSuccessfully() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", LoremIpsum.getInstance().getTitle(3));
        jsonObject.put("body", LoremIpsum.getInstance().getParagraphs(1, 1));
        jsonObject.put("picture", "https://dummyimage.com/300x300&text=" + LoremIpsum.getInstance().getFirstName());

        given()
                .body(jsonObject)
                .log().body()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + getBearerToken())
                .log().uri()
                .when()
                .post("/article/create")
                .then()
                .log().body()
                .statusCode(200)
                .body("message", equalTo("Article has been created"))
                .body("success", equalTo(true));
    }
}