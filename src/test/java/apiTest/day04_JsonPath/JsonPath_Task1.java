package apiTest.day04_JsonPath;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;

public class JsonPath_Task1 {

    /*
    TASK
Given accept type is json
And Pam user id is 111
When user sends a GET request to /allusers/getbyid/{id}
Then the status Code should be 200
And Content type json should be "application/json; charset=UTF-8"
And user's company should be "GHAN Software"
And user's id should be 111
And SQL should be the one of the user's skills
And user's education should be ODTU, Delft University
And user's email should be thomas@test.com
     */
    @BeforeClass
    public void setUpAPI() {
        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void test1() {
        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 111)
                .when()
                .get("/allusers/getbyid/{id}");


        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json; charset=UTF-8");

        response.then().assertThat().contentType("application/json; charset=UTF-8").statusCode(200);

        JsonPath jsonPath=response.jsonPath();

        String companyName=jsonPath.getString("company[0]");
        System.out.println("companyName = " + companyName);

        Assert.assertEquals(companyName,"GHAN Software");

//        String body = response.body().asString();
//        Assert.assertTrue(body.contains("GHAN Software"));

        int id=jsonPath.getInt("id[0]");
        System.out.println("id = " + id);
        Assert.assertEquals(id,111);

        String skills = jsonPath.getString("skills[0]");
        System.out.println("skills = " + skills);
        Assert.assertTrue(skills.contains("SQL"));

        String skillSQL = jsonPath.get("skills[0][4]");
        System.out.println("skillSQL = " + skillSQL);
        Assert.assertEquals(skillSQL,"SQL");

        List<String> skillList=jsonPath.getList("skills[0]");
        String skillCypress=skillList.get(3);
        System.out.println("skillCypress = " + skillCypress);
        Assert.assertEquals(skillCypress,"Cypress");

        String education=jsonPath.getString("education[0]");
        String education1=jsonPath.getString("education[0][0].school");
        String education2=jsonPath.getString("education[0][1]");

        System.out.println("education = " + education);
        System.out.println("education1 = " + education1);
        System.out.println("education2 = " + education2);

        String school1=jsonPath.getString("education.school[0][0]");
        String school2=jsonPath.getString("education.school[0][1]");

        System.out.println("school1 = " + school1);
        System.out.println("school2 = " + school2);

        Assert.assertEquals(school1,"ODTU");
        Assert.assertEquals(school2,"Delft University");

        String email=jsonPath.getString("email[0]");
        Assert.assertEquals(email,"thomas@test.com");

        String location1=jsonPath.getString("experience.location[0][2]");
        System.out.println("location1 = " + location1);
        String location2=jsonPath.getString("experience[0][2].location");
        System.out.println("location2 = " + location2);

        Map<String,Object> mapExperince=jsonPath.getMap("experience[0][2]");
        System.out.println("mapExperince.get(\"location3\") = " + mapExperince.get("location"));


    }
}
