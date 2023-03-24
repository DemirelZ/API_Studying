package apiTest.day_08_PutPatchDelete;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class DeleteExperience {

    @BeforeClass
    public void beforeClass() {

        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void deleteUserExperience(){

        Response response=given().accept(ContentType.JSON)
                .and()
                .pathParam("id", 231)
                .and()
                .queryParam("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdXQiOiJhRm0iLCJsaW5rIjoia3JhZnR0ZWNoZXhsYWIuY29tIiwidXNlcmlkIjoiMzAwIiwic3RhcnQiOjE2NzQ4Mzg4NzEsImVuZHMiOjE2NzU0NDM2NzF9.PEoLJcbTX1Qscr8I31LQSiMj0dTVjCWeQXYC2YuzryijQmDS8drL480CVbM0aL6AKAK1aIXPLt7h7yPay67mXA")
                .when()
                .delete("/experience/delete/{id}").prettyPeek();

    }



}
