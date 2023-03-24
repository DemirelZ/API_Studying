package apiTest.day04_JsonPath;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.baseURI;


/*

TASK
Given accept type is json
And Path param user id is 111
When user sends a GET request to /allusers/getbyid/{id}
Then the status Code should be 200
And Content type json should be "application/json; charset=UTF-8"
And user's company should be "GHAN Software"
And user's id should be 111
And SQL should be the one of the user's skills
And user's education should be ODTU, Delft University
And user's email should be thomas@test.com
 */


public class Assigment1 {

    @BeforeClass
    public void beforeClass(){

        baseURI="https://www.krafttechexlab.com/sw/api/v1";
    }


    @Test
    public void test1(){

        Response response= RestAssured.given(). accept(ContentType.JSON)
                .pathParam("id", 111)
                .when()
                .get("/allusers/getbyid/{id}");

        response.then().assertThat().contentType("application/json; charset=UTF-8").statusCode(200);

        JsonPath jsonPath=response.jsonPath();

        String companyName=jsonPath.getString("company[0]");
        Assert.assertEquals(companyName, "GHAN Software" );

        int id=jsonPath.getInt("id[0]");
        Assert.assertEquals(id,111);

        String skillSQL=jsonPath.getString("skills[0][4]");
        Assert.assertEquals(skillSQL,"SQL");

//        String school1=jsonPath.getString("education.school[0]");
//        System.out.println("school1 = " + school1);




    }


}
