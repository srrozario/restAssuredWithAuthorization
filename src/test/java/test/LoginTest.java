package test;

import baseTest.BaseTest;
import com.thedeanda.lorem.LoremIpsum;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LoginTest extends BaseTest {
    @Test
    public void successfulLogin(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "superman@gmail.com");
        jsonObject.put("password", "123456");

        given()
                .body(jsonObject)
                .contentType(ContentType.JSON)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .body("message", equalTo("Login success"))
                .log().body();
    }

    @Test
    public void unsuccessfulLogin5(){
        String email = LoremIpsum.getInstance().getEmail();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", "123456");

        given()
                .body(jsonObject)
                .contentType(ContentType.JSON)
                .when()
                .post("/login")
                .then()
                .statusCode(400)
                .body("message", equalTo("User does not exist"))
                .log().body();
    }
}
