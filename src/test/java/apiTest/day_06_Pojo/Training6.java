package apiTest.day_06_Pojo;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.collections.Maps;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Training6 {

    @BeforeClass
    public void beforeClass(){

       // baseURI="https://www.krafttechexlab.com/sw/api/v1";
        baseURI="https://petstore.swagger.io/v2";
    }
    @Test
    public void task1() {

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
                .queryParam("pagesize", 50)
                .queryParam("page", 2)
                .get("/allusers/alluser");

        Assert.assertEquals(response.statusCode(), 200);


        List<Map<String, Object>> allUser=response.body().as(List.class);

        List<Map<String, Object>> user7= (List<Map<String, Object>>) allUser.get(7).get("experience");

        String company1 = (String) user7.get(0).get("company");
        Assert.assertEquals(company1,"Ghan IT Com");
        String company2 = (String) user7.get(1).get("company");
        Assert.assertEquals(company2,"GHAN II IT BV");

    }

    @Test
    public void task2(){

        Response response = given().accept(ContentType.JSON)
                .pathParam("username", "jake24")
                .when().get("/user/{username}");

        Assert.assertEquals(response.statusCode(),200);

        TrainingPojo oneUser=response.body().as(TrainingPojo.class);

        System.out.println("oneUser.getUsername() = " + oneUser.getUsername());
        System.out.println("oneUser.getEmail() = " + oneUser.getEmail());


    }

    @Test
    public void gsonToMap(){

        Gson gson=new Gson();

        String myJsonBody="{\n" +
                "    \"id\": 9222968140497201875,\n" +
                "    \"username\": \"jake24\",\n" +
                "    \"firstName\": \"jake\",\n" +
                "    \"lastName\": \"Master\",\n" +
                "    \"email\": \"jake@gmail.com\",\n" +
                "    \"password\": \"Test1234\",\n" +
                "    \"phone\": \"55512345\",\n" +
                "    \"userStatus\": 21\n" +
                "}";

        System.out.println("myJsonBody = " + myJsonBody);


        Map<String, Object> dataMap=gson.fromJson(myJsonBody, Map.class);
        System.out.println("dataMap = " + dataMap);

        System.out.println("dataMap.get(\"firstname\") = " + dataMap.get("firstName"));


        TrainingPojo oneUser=gson.fromJson(myJsonBody, TrainingPojo.class);
        System.out.println("oneUser = " + oneUser);


        TrainingPojo trainingPojo=new TrainingPojo(11,"jadfbke24","javdfvdke",
                "Mavdfvster","jake@vdfvdfgmail","Testvfdvdf1234",
                "55512345",21);

        String jsonUser= gson.toJson(trainingPojo);
        System.out.println("jsonUser = " + jsonUser);

    }


}
