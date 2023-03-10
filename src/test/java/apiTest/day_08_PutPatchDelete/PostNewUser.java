package apiTest.day_08_PutPatchDelete;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class PostNewUser {

    @BeforeClass
    public void beforeClass() {

        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void newUser(){

        String body="{\n" +
                "  \"name\": \"Aliihsan\",\n" +
                "  \"email\": \"aliihsan@krafttechexlab.com\",\n" +
                "  \"password\": \"aliihsan\",\n" +
                "  \"about\": \"About Me\",\n" +
                "  \"terms\": \"10\"\n" +
                "}";


        Response response = given().accept(ContentType.JSON)
                .body(body)
                .when().log().all()
                .post("/allusers/register").prettyPeek();

        String token=response.path("token");
        System.out.println("token = " + token);


        //"id": 300,
        //"name": "Aliihsan",
        //"email": "aliihsan@krafttechexlab.com",

//  eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdXQiOiJhRm0iLCJsaW5rIjoia3JhZnR0ZWNoZXhsYWIuY29tIiwidXNlcmlkIjoiMzAwIiwic3RhcnQiOjE2NzQ4Mzg4NzEsImVuZHMiOjE2NzU0NDM2NzF9.PEoLJcbTX1Qscr8I31LQSiMj0dTVjCWeQXYC2YuzryijQmDS8drL480CVbM0aL6AKAK1aIXPLt7h7yPay67mXA
    }

}
