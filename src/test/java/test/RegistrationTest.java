package test;

import baseTest.BaseTest;
import com.thedeanda.lorem.LoremIpsum;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RegistrationTest extends BaseTest {
    @Test
    public void registrationShouldFailed() {
        String name = LoremIpsum.getInstance().getName();
        String email = LoremIpsum.getInstance().getEmail();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("email", email);
        jsonObject.put("phone", "3249861237");
        jsonObject.put("password", "123456");
        jsonObject.put("password_confirmation", "123456");

        given()
                .body(jsonObject)
                .contentType(ContentType.JSON)
                .when()
                .post("/register")
                .then()
                .statusCode(404)
                .body("message", equalTo("API not found or wrong method"))
                .log().body();
    }
}
