package apiTest.day_07_POST_Request.training;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PostEducation {

    @BeforeClass
    public void beforeClass() {

        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void postNewUser() {

        NewUserInfo1 newUserInfo1 = new NewUserInfo1("zehra6", "zehra6@krafttechexlab.com", "123456789", "Çalışkan Kadir", "4");

        Response response = given().accept(ContentType.JSON)//bana json gönder
                .and()
                .contentType(ContentType.JSON)//sana json gönderiyorum
                .and()
                .body(newUserInfo1)
                .when()
                .post("allusers/register");

        assertEquals(response.statusCode(), 200);
        response.prettyPrint();

        String token = response.path("token");

        String educationBody = "{\n" +
                "  \"school\": \"Krfatech\",\n" +
                "  \"degree\": \"Bootcamp\",\n" +
                "  \"study\": \"SDET\",\n" +
                "  \"fromdate\": \"2023-01-01\",\n" +
                "  \"todate\": \"2024-01-01\",\n" +
                "  \"current\": \"false\",\n" +
                "  \"description\": \"Description\"\n" +
                "}";

        response = given().accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .queryParam("token", token)
                .and()
                .body(educationBody)
                .when()
                .post("/education/add");

        assertEquals(response.statusCode(), 200);
        response.prettyPrint();
    }

    @Test
    public void postNewUser1() {

        String name = "Zehra9";
        String email = "zehra9@krafttechexlab.com";
        String password = "1234567889";
        String about = "Çalışkan zehra";
        String terms = "12";

        Map<String, Object> requestMap = new HashMap<>();

        requestMap.put("name", name);
        requestMap.put("email", email);
        requestMap.put("password", password);
        requestMap.put("about", about);
        requestMap.put("terms", terms);

        Response response = given().accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(requestMap) // -->serialization
                .when()
                .post("/allusers/register");

        Assert.assertEquals(response.statusCode(), 200);

        response.prettyPrint();

        String token = response.path("token");

        Map<String, Object> educationBody = new HashMap<>();

        educationBody.put("school", "Kraft");
        educationBody.put("degree", "bootcamp");
        educationBody.put("study", "bootcamp");
        educationBody.put("fromdate", "2023-01-01");
        educationBody.put("todate", "2024-01-01");
        educationBody.put("current", "false");
        educationBody.put("description", "olacak");

        response = given().accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .queryParam("token", token)
                .and()
                .body(educationBody)
                .when()
                .post("/education/add");

        assertEquals(response.statusCode(), 200);
        response.prettyPrint();

        //verify body

        int educationid = response.path("id");

        response = given().accept(ContentType.JSON)
                .and()
                .queryParam("token", token)
                .and()
                .pathParam("id", educationid)
                .when()
                .get("/education/getbyid/{id}");

        response.prettyPrint();

        assertEquals(response.statusCode(), 200);

        assertEquals(response.path("school"), "Kraft");


        //hancrest matcher

        given().accept(ContentType.JSON)
                .and()
                .queryParam("token", token)
                .and()
                .pathParam("id", educationid)
                .when()
                .get("/education/getbyid/{id}")
                .then()
                .assertThat()
                .body("school", equalTo("Kraft"),
                        "degree", equalTo("bootcamp")).log().all();


    }
}
