package apiTest.day04_JsonPath;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
public class ErenHoca {

    @BeforeClass
    public void beforeClass(){

        baseURI="https://www.krafttechexlab.com/sw/api/v1";
    }


    @Test
    public void test1(){

        Response response=RestAssured.given().accept(ContentType.JSON)
                .queryParam("pagesize", 15 )
                .queryParam("pagesize", 1 )
                .when()
                .get("/allusers/alluser");

        response.prettyPrint();
    }

    @Test
    public void test2(){

        Response response=given().accept(ContentType.JSON)
                .pathParam("id", 1)
                .get("allusers/getbyid/{id}");

        response.then().assertThat().statusCode(200);
        System.out.println("response.statusCode() = " + response.statusCode());

        System.out.println("response.body().asString() = " + response.body().asString());


    }

    @Test
    public void test3(){

        given().accept(ContentType.JSON)
                .pathParam("id", 1)
                .get("allusers/getbyid/{id}")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=UTF-8");
    }

    @Test
    public void getrequestQueryParam(){

        Response response = given()
                .queryParam("pagesize", 10)
                .queryParam("page", 1)
                .when()
                .get("allusers/alluser");

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(response.body().asString().contains("aFm"));

    }
    @Test
    public void test5(){

        Response response = given()
                .pathParam("id", 1)
                .when().get("/allusers/givenbyid/{id}");

        boolean date = response.headers().hasHeaderWithName("Date");
        Assert.assertTrue(date);




    }


}
