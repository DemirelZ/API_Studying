package apiTest.day03_PathMethod;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

public class UserGetRequestWithPathMethod {

    @BeforeClass
    public void beforeclass() {
        RestAssured.baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void testWithPathMethod(){

        Response response=given().accept(ContentType.JSON)
                .pathParam("id",111)
                .when().log().all()
                .get("/allusers/getbyid/{id}");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json; charset=UTF-8");

        //print each value
        System.out.println("response.body().path(name).toString() = " + response.body().path("name").toString());
        System.out.println("response.path(id).toString() = " + response.path("id").toString());
        System.out.println("response.path(job).toString() = " + response.path("job").toString());

        int id=response.path("id[0]");
        String name=response.path("name[0]");
        String jobName=response.path("job[0]");

        Assert.assertEquals(id,111);
        Assert.assertEquals(name,"Thomas Eduson");
        Assert.assertEquals(jobName,"Developer");
    }


    /*

    TASK
    Given accept type json
    And query  parameter value pagesize 50
    And query  parameter value page 1
    When user sends GET request to /allusers/alluser
    Then response status code should be 200
    And response content-type application/json; charset=UTF-8
    Verify the first id is 1
    Verify the first name is aFm
    Verify the last id is 102
    Verify the last name is GHAN
     */
    @Test
    public void testAllUSersWithPAth(){

        Response response= given().accept(ContentType.JSON)
                .queryParam("pagesize",50)
                .queryParam("page",1)
                .when()
                .get("/allusers/alluser");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(), "application/json; charset=UTF-8");
        Assert.assertEquals(response.getHeader("Content-Type"), "application/json; charset=UTF-8");

        int id=response.path("id[0]");
        Assert.assertEquals(id,1);

        String name=response.path("name[0]");
        Assert.assertEquals(name, "aFm");

        int lastId=response.path("id[-1]");
        Assert.assertEquals(lastId,102);

        String lastName=response.path("name[-1]");
        Assert.assertEquals(lastName, "GHAN");
    }

/*
    Given accept type json
    When user sends a get request to https://demoqa.com/BookStore/v1/Books
    Then status code should be 200
    And content typr should be application/json; charset=utf-8
    And the first book isbn should be 9781449325862
    And the first book publisher should be O'Reilly Media

    https://demoqa.com/swagger/#/BookStore/BookStoreV1BooksGet   --> swagger adresi

     */

@Test
    public void demoqa(){

    Response response=given().accept(ContentType.JSON)
            .get("https://demoqa.com/BookStore/v1/Books");



    Assert.assertEquals(response.statusCode(), 200);
    Assert.assertEquals(response.contentType(), "application/json; charset=utf-8");

    String isbn=response.path("books.isbn[0]");
    Assert.assertEquals(isbn,"9781449325862");

    String publisher=response.path("books.publisher[0]");
    Assert.assertEquals(publisher,"O'Reilly Media");

}











    }





