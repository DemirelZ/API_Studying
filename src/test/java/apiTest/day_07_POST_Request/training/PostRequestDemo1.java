package apiTest.day_07_POST_Request.training;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.testng.Assert.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.management.ManagementPermission;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class PostRequestDemo1 {

    @BeforeClass
    public void beforeClass() {

        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void postNewUser() {

        String jsonBody = "{\n" +
                "  \"name\": \"zehra1\",\n" +
                "  \"email\": \"zehra1@krafttechexlab.com\",\n" +
                "  \"password\": \"zehra1\",\n" +
                "  \"about\": \"About Me\",\n" +
                "  \"terms\": \"10\"\n" +
                "}";

        Response response = given().accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(jsonBody)
                .when()
                .post("allusers/register");

        assertEquals(response.statusCode(), 200);
        response.prettyPrint();

        assertTrue(response.body().asString().contains("token"));

    }

    @Test
    public void postNewUser2(){

        Map<String, Object> requestMap=new HashMap<>();

        requestMap.put("name", "zehra2");
        requestMap.put("email", "zehra2@krafttechexlab.com");
        requestMap.put("password", "123467");
        requestMap.put("about", "About what?");
        requestMap.put("terms", "3");

        Response response = given().accept(ContentType.JSON)//bana json gönder
                .and()
                .contentType(ContentType.JSON)//sana json gönderiyorum
                .and()
                .body(requestMap)
                .when()
                .post("allusers/register");

        assertEquals(response.statusCode(), 200);
        response.prettyPrint();

        assertTrue(response.body().asString().contains("token"));

    }

    @Test
    public void postNewUser3(){

        NewUserInfo1 newUserInfo1=new NewUserInfo1();

        newUserInfo1.setName("zehra3");
        newUserInfo1.setEmail("zehra3@krafttechexlab.com");
        newUserInfo1.setPassword("123456789");
        newUserInfo1.setAbout("about what");
        newUserInfo1.setTerms("12");

        Response response = given().accept(ContentType.JSON)//bana json gönder
                .and()
                .contentType(ContentType.JSON)//sana json gönderiyorum
                .and()
                .body(newUserInfo1)
                .when()
                .post("allusers/register");

        assertEquals(response.statusCode(), 200);
        response.prettyPrint();

        assertTrue(response.body().asString().contains("token"));
    }

    @Test
    public void postNewUser4(){

        NewUserInfo1 newUserInfo1=new NewUserInfo1("zehra4","zehra4@krafttechexlab.com","123456789","Çalışkan Kadir","4");

        Response response = given().accept(ContentType.JSON)//bana json gönder
                .and()
                .contentType(ContentType.JSON)//sana json gönderiyorum
                .and()
                .body(newUserInfo1)
                .when()
                .post("allusers/register");

        assertEquals(response.statusCode(), 200);
        response.prettyPrint();

        assertTrue(response.body().asString().contains("token"));



    }

}
