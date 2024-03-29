package apiTest.day_06_Pojo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;

public class POJO_Deserialization {

    /*
    {
    "id": 9222968140497198741,
    "username": "Jake23",
    "firstName": "Jake",
    "lastName": "Master",
    "email": "jake@gmail.com",
    "password": "Test1234",
    "phone": "55512345",
    "userStatus": 21
}
     */

    @BeforeClass
    public void beforeClass(){

        baseURI="https://petstore.swagger.io/v2";
    }

    @Test
    public void oneUserPetStore(){

        Response response = RestAssured.given().accept(ContentType.JSON)
                .and()
                .pathParam("username", "Jake23")
                .when()
                .get("/user/{username}");

        System.out.println("response.statusCode() = " + response.statusCode());

        //JSON to our petstore object
        PetStoreUser oneUser=response.body().as(PetStoreUser.class);

        //print all info
        System.out.println("oneUser.getId() = " + oneUser.getId());
        System.out.println("oneUser.getUsername() = " + oneUser.getUsername());
        System.out.println("oneUser.getFirstName() = " + oneUser.getFirstName());
        System.out.println("oneUser.getLastName() = " + oneUser.getLastName());
        System.out.println("oneUser.getEmail() = " + oneUser.getEmail());
        System.out.println("oneUser.getPhone() = " + oneUser.getPhone());

        //verify all info

        Assert.assertEquals(oneUser.getId(), 9.2229681404971991E18 );
        Assert.assertEquals(oneUser.getUsername(), "Jake23" );
        Assert.assertEquals(oneUser.getFirstName(), "Jake" );


    }



}
