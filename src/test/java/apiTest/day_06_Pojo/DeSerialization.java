package apiTest.day_06_Pojo;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class DeSerialization {

    @BeforeClass
    public void beforeClass(){

        baseURI="https://www.krafttechexlab.com/sw/api/v1";
    }
    @Test
    public void task1(){

        /**
         * end point => /allusers/alluser -> GET All User
         * page size : 50
         * page : 2
         * The company in the 8. user's experience part
         * verify this informat,on
         * 1.company -> Ghan IT Com
         * 2.company -> GHAN II IT BV
         */

        Response response = given().accept(ContentType.JSON)
                .queryParams("pagesize", 50)
                .queryParam("page", 2)
                .get("/allusers/alluser");

        Assert.assertEquals(response.statusCode(), 200 );

        //de-serialization Json to javacollection

        List<Map<String,Object>> allUsers= response.body().as(List.class);
        System.out.println("allUsers = " + allUsers);

        List<Map<String, Object>> experienceUsers= (List<Map<String, Object>>) allUsers.get(7).get("experience");

        String company1 = (String) experienceUsers.get(0).get("company");
        Assert.assertEquals(company1, "Ghan IT Com", "FAILED Company1" );

        String company2 = (String) experienceUsers.get(1).get("company");
        Assert.assertEquals(company2, "GHAN II IT BV", "FAILED Company2" );


    }


}
