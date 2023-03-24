package apiTest.day05_HamcrestMatcher;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Training3 {

    @BeforeClass
    public void beforeClass(){

        baseURI="https://www.krafttechexlab.com/sw/api/v1";
    }

    /*
        given accept type is json
        And path param id is 111
        When user sends a get request to /allusers/getbyid/{id}
        Then status code should be 200
        And content type should be application/json; charset=UTF-8
        And json data has following:
         "id"= 111
         "name"= "Thomas Eduson"
         "job"="Developer"
         */

    @Test
    public void test1(){

        given().accept(ContentType.JSON)
                .pathParam("id", 111)
                .get("/allusers/getbyid/{id}")

                .then().assertThat().statusCode(200).contentType("application/json; charset=UTF-8")
                .and()
                .body("id[0]", equalTo(111),
                        "name[0]", equalTo("Thomas Eduson"),
                        "job[0]", equalTo("Developer"));
    }

    /*
        given accept type is json
        And query param pagesize is 50
        And query param page is 1
        When user sends a get request to /allusers/alluser
        Then status code should be 200
        And content type should be application/json; charset=UTF-8
        And response header content-length should be 8653
        And response header server should be Apache/2
        And response headers has Date
        And json data should have "GHAN","aFm","Sebastian"
        And json data should have "QA" for job
        And json data should have "Junior Developer1" for first user's experience job
         */
    @Test
    public void test2(){

        given().accept(ContentType.JSON)
                .queryParam("pagesize", 50)
                .queryParam("page", 1)
                .get("/allusers/alluser")

                .then().assertThat().statusCode(200).contentType("application/json; charset=UTF-8")
                .and()
                .header("content-length", equalTo("8653"))
                .header("server", equalTo("Apache/2"))
                .headers("Date", notNullValue())
                .body("name", hasItems("GHAN","aFm","Sebastian"))
                .body("job", hasItems("QA"))
                .body("experience[0].job[2]", equalTo("Junior Developer"));

    }

    @Test
    public void test3(){

        Response response=given().accept(ContentType.JSON)
                .get("https://demoqa.com/Account/v1/User/11");

        response.then().assertThat().statusCode(401);

        Map<String, Object> oneMap=response.body().as(Map.class);

        System.out.println("oneMap = " + oneMap);

        Assert.assertEquals(oneMap.get("code"), "1200");
        Assert.assertEquals(oneMap.get("message"), "User not authorized!");
    }

    @Test
    public void test4(){

        Response response = given().accept(ContentType.JSON)
                .queryParam("pagesize", 50)
                .queryParam("page", 1)
                .get("/allusers/alluser");

        response.then().assertThat().statusCode(200);

        List<Map<String, Object>> allUsersMaps=response.body().as(List.class);

        //System.out.println("allUsersMaps = " + allUsersMaps);

        String name2 = (String) allUsersMaps.get(1).get("name");
        Assert.assertEquals(name2,"isa akyuz");

        List<String> skills = (List<String>) allUsersMaps.get(0).get("skills");
        System.out.println("skills = " + skills);

        String sphp = skills.get(0);
        Assert.assertEquals(sphp,"PHP");

        List<Map<String, Object>> experience = (List<Map<String, Object>>) allUsersMaps.get(0).get("experience");

        System.out.println("experience.get(2).get(\"job\") = " + experience.get(2).get("job"));

        List<String> harunSkills= (List<String>) allUsersMaps.get(2).get("skills");
        System.out.println("harunSkills = " + harunSkills);

        System.out.println("harunSkills.get(3) = " + harunSkills.get(3));


    }


}
