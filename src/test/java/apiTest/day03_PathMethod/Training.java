package apiTest.day03_PathMethod;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Training {

    String exlabURL = "https://www.krafttechexlab.com/sw/api/v1";

    @Test
    public void test1() {

        Response response = given().accept(ContentType.JSON)
                .queryParam("pagesize", 50)
                .queryParam("page", 1)
                .get(exlabURL+"/allusers/alluser");

        response.prettyPrint();

    }

    @Test
    public void test2() {

        Response response=given().accept(ContentType.JSON)
                .pathParam("id", 111)
                .when()
                .get(exlabURL+"/allusers/getbyid/{id}");

        response.prettyPrint();

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(), "application/json; charset=UTF-8");
        Assert.assertTrue(response.body().asString().contains("Thomas"));
        Assert.assertEquals(response.header("Content-Length"), "636");

    }

    @BeforeClass
    public void beforeClass(){

        baseURI="https://www.krafttechexlab.com/sw/api/v1";

    }

    @Test
    public void test3() {

        Response response=given().accept(ContentType.JSON)
                .pathParam("id", 444)
                .when().log().all()
                .get("/allusers/getbyid/{id}");

        Assert.assertEquals(response.statusCode(), 404);
        Assert.assertEquals(response.contentType(), "application/json; charset=UTF-8");
        Assert.assertTrue(response. body().asString().contains("Record"));
    }

    @Test
    public void test4() {

       Response response=given().accept(ContentType.JSON)
               .queryParam("name", "Thomas Eduson")
               .queryParam("skills", "Cypress")
               .queryParam("pagesize", 50)
               .queryParam("page", 1)
               .when().log().all()
               .get("/allusers/alluser");

       Assert.assertEquals(response.statusCode(),200);
       Assert.assertEquals(response.contentType(),"application/json; charset=UTF-8");
       Assert.assertTrue(response.body().asString().contains("Developer"));



    }
    @Test
    public void test5() {

        Map<String, Object> mapBody=new HashMap<>();


        mapBody.put("name", "Thomas Eduson");
        mapBody.put("skills", "Cypress");
        mapBody.put("pagesize", 50);
        mapBody.put("page", 1);

        Response response=given().accept(ContentType.JSON)
                .queryParams(mapBody)
                .when().log().all()
                .get("/allusers/alluser");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json; charset=UTF-8");
        Assert.assertTrue(response.body().asString().contains("Developer"));




    }

    @Test
    public void test6() {



        Response response=given().accept(ContentType.JSON)
                .pathParam("id",111)
                        .when().log().all()
                        .get("/allusers/getbyid/{id}");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json; charset=UTF-8");

        System.out.println("response.body().path(\"name\").toString() = " + response.body().path("name").toString());
        System.out.println("response.path(\"job\").toString() = " + response.path("job").toString());


        int id = response.path("id[0]");
        String name = response.path("name[0]");
        String job = response.path("job[0]");
        String skills = response.path("skills[0][2]");
        System.out.println("skills = " + skills);

        Assert.assertEquals(id,111);
        Assert.assertEquals(name,"Thomas Eduson");
        Assert.assertEquals(job,"Developer");


    }






}
