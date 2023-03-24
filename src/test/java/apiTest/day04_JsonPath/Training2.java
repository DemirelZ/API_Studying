package apiTest.day04_JsonPath;

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

public class Training2 {


    @BeforeClass
    public void beforeClass(){

        baseURI="https://www.krafttechexlab.com/sw/api/v1";
    }


    @Test
    public void test1(){

        Response response=given().accept(ContentType.JSON)
                .queryParam("pagesize", 50)
                .queryParam("page", 1)
                .get("/allusers/alluser");

        response.then().assertThat().contentType("application/json; charset=UTF-8").statusCode(200);

        int firstId=response.path("id[0]");
        Assert.assertEquals(firstId,1);

        String firstName=response.path("name[0]");
        Assert.assertEquals(firstName,"aFm");

        int lastId=response.path("id[-1]");
        Assert.assertEquals(lastId,102);

        String lastName=response.path("name[-1]");
        Assert.assertEquals(lastName,"GHAN");
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
    public void test2(){

        Response response=given().accept(ContentType.JSON)
                .get("https://demoqa.com/BookStore/v1/Books");

        response.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8");

        String isbn=response.path("books.isbn[0]");
        Assert.assertEquals(isbn, "9781449325862");

        String name=response.path("books.publisher[0]");
        Assert.assertEquals(name, "O'Reilly Media");
    }

    @Test
    public void test3(){

        Response response=given().accept(ContentType.JSON)
                .pathParam("id",111)
                .when().log().all()
                .get("/allusers/getbyid/{id}");

        response.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8");


        JsonPath jsonPath=response.jsonPath();

        String name=jsonPath.getString("name[0]");
        int id= jsonPath.getInt("id[0]");
        String email= jsonPath.getString("email[0]");

        Assert.assertEquals(name, "Thomas Eduson");
        Assert.assertEquals(id, 111);
        Assert.assertEquals(email, "thomas@test.com");
    }

    @Test
    public void test4() {

        Response response = given().accept(ContentType.JSON)
                .queryParam("pagesize", 50)
                .queryParam("page", 1)
                .get("/allusers/alluser");

        response.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8");

        JsonPath jsonPath=response.jsonPath();

        String secondNmae=jsonPath.getString("name[1]");

       // String job= jsonPath.getString("experience.job[0]");


        List<String> joblist=new ArrayList<>();
        joblist.add("Junior Developer1");
        joblist.add("Junior Developer1");
        joblist.add("Junior Developer");

        List<String> jobs = jsonPath.getList("experience.job[0]");

        Assert.assertEquals(joblist,jobs);
        Assert.assertEquals(secondNmae,"isa akyuz");


    }
@Test
    public void test5() {

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 111)
                .when().log().all()
                .get("/allusers/getbyid/{id}");

        response.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8");


        JsonPath jsonPath = response.jsonPath();

        List<String> skilsAll=jsonPath.getList("skills[0]");

        String skillPhp=jsonPath.getString("skills[0][0]");

        System.out.println("skillPhp = " + skillPhp);
        System.out.println("skilsAll = " + skilsAll);

    }










}
