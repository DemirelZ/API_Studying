package apiTest.day_07_POST_Request;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class PostRequestDemo {

    @BeforeClass
    public void beforeClass() {

        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void postNewUser() {

        String jsonBody = "{\n" +
                "  \"name\": \"Kadir1\",\n" +
                "  \"email\": \"kadir1@krafttechexlab.com\",\n" +
                "  \"password\": \"123467\",\n" +
                "  \"about\": \"Çalışkan Kadir\",\n" +
                "  \"terms\": \"10\"\n" +
                "}";

        Response response = given().accept(ContentType.JSON) // hey api send me body as json body format
                .and()
                .contentType(ContentType.JSON) //Hey api i am sending json body
                .and()
                .body(jsonBody)
                .when()
                .post("/allusers/register");

        Assert.assertEquals(response.statusCode(), 200);

        Assert.assertTrue(response.body().asString().contains("token"));
    }

    @Test
    public void postNewUser2() {

        Map<String, Object> requestMap = new HashMap<>();

        requestMap.put("name", "kadir2");
        requestMap.put("email", "kadir2@krafttechexlab.com");
        requestMap.put("password", "123467");
        requestMap.put("about", "Çalışkan Kadir");
        requestMap.put("terms", "3");

        Response response = given().accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(requestMap) // -->serialization
                .when()
                .post("/allusers/register");

        Assert.assertEquals(response.statusCode(), 200);

        response.prettyPrint();

        Assert.assertTrue(response.body().asString().contains("token"));

    }

    @Test
    public void postNewUser3() {

        NewUserInfo newUserInfo = new NewUserInfo();

        newUserInfo.setName("Kadir3");
        newUserInfo.setEmail("kadir3@krafttechexlab.com");
        newUserInfo.setPassword("123456789");
        newUserInfo.setAbout("Çalışkan Kadir");
        newUserInfo.setTerms("4");

        Response response = given().accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(newUserInfo) // -->serialization
                .when()
                .post("/allusers/register");

        Assert.assertEquals(response.statusCode(), 200);

        response.prettyPrint();

        Assert.assertTrue(response.body().asString().contains("token"));

    }

    @Test
    public void postNewUser4(){

        NewUserInfo newUserInfo=new NewUserInfo("Kadir4","kadir4@krafttechexlab.com","123456789","Çalışkan Kadir","4");

        Response response = given().accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(newUserInfo) // -->serialization
                .when()
                .post("/allusers/register");

        Assert.assertEquals(response.statusCode(), 200);

        response.prettyPrint();

        Assert.assertTrue(response.body().asString().contains("token"));
    }


}
