package baseTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BaseTest {
    @BeforeClass
    public void setup(){
        RestAssured.baseURI ="https://apingweb.com";
        RestAssured.basePath="/api";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public String getBearerToken(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "superman@gmail.com");
        jsonObject.put("password", "123456");

        return  given()
                .body(jsonObject)
                .contentType(ContentType.JSON)
                .when()
                .post("/login")
                .then()
                // .statusCode(200)
                .body("message", equalTo("Login success"))
                .log().body()
                .extract().jsonPath().getString("token");
    }
}
