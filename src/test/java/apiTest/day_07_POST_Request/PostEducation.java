package apiTest.day_07_POST_Request;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class PostEducation {

    @BeforeClass
    public void beforeClass() {

        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void postNewUser(){

        NewUserInfo newUserInfo=new NewUserInfo("Kadir6","kadir6@krafttechexlab.com",
                "123456789","Çalışkan Kadir","4");

        Response response = given().accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(newUserInfo) // -->serialization
                .when()
                .post("/allusers/register");

        Assert.assertEquals(response.statusCode(), 200);

        response.prettyPrint();

        String token=response.path("token");

        String educationBody="{\n" +
                "  \"school\": \"Kraft\",\n" +
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
                .body(educationBody)
                .and()
                .queryParam("token", token)
                .when()
                .post("/education/add");


         response.prettyPrint();

    }

    @Test
    public void postNewUserAndVerify(){

         String name="Kadir10";
         String email="kadir10@krafttechexlab.com";
         String password="123456789";
         String about="Çalışkan Kadir";
         String terms="12";

        Map<String, Object> requestMap=new HashMap<>();

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

        String token=response.path("token");
       // String userid=response.path("id");

        Map<String, Object> educationBody=new HashMap<>();

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
                .body(educationBody)
                .and()
                .queryParam("token", token)
                .when()
                .post("/education/add");

        response.prettyPrint();
        Assert.assertEquals(response.statusCode(),200);

        //verify body

        int id=response.path("id");

        response=given().accept(ContentType.JSON)
                .and()
                .queryParam("token", token)
                .pathParam("id", id)
                .when()
                .get("education/getbyid/{id}");

        response.prettyPrint();

        Assert.assertEquals(response.statusCode(),200);

        //verify with path

        //System.out.println("userid = " + userid);
        System.out.println("education_id = " + id);
        Assert.assertEquals(response.path("school"), "Kraft");
        //Assert.assertEquals(response.path("userid"), userid);

        //verify using hamcrest matcher

        given().accept(ContentType.JSON)
                .and()
                .queryParam("token", token)
                .pathParam("id", id)
                .when()
                .get("education/getbyid/{id}")
                .then()
                .assertThat()
                .body("school", equalTo("Kraft"),
                        //"userid", equalTo(userid),
                        "study", equalTo("bootcamp")).log().all();


    }

}
