package apiTest.day_08_PutPatchDelete;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.BooleanUtils.and;

public class PostNewExperience {

    @BeforeClass
    public void beforeClass() {

        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void newExperience(){

        String body="{\n" +
                "  \"job\": \"King QA\",\n" +
                "  \"company\": \"HB\",\n" +
                "  \"location\": \"USA\",\n" +
                "  \"fromdate\": \"2023-11-11\",\n" +
                "  \"todate\": \"2026-11-11\",\n" +
                "  \"current\": \"false\",\n" +
                "  \"description\": \"Description\"\n" +
                "}";

        Response response = given().accept(ContentType.JSON)
                .and()
                .queryParam("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdXQiOiJhRm0iLCJsaW5rIjoia3JhZnR0ZWNoZXhsYWIuY29tIiwidXNlcmlkIjoiMzAwIiwic3RhcnQiOjE2NzQ4Mzg4NzEsImVuZHMiOjE2NzU0NDM2NzF9.PEoLJcbTX1Qscr8I31LQSiMj0dTVjCWeQXYC2YuzryijQmDS8drL480CVbM0aL6AKAK1aIXPLt7h7yPay67mXA")
                .and()
                .body(body)
                .when()
                .post("experience/add");

        response.prettyPrint();

        // 231 experience id1
        // 241 experience id2

    }





}
