package apiTest.day05_HamcrestMatcher;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class JsonToJavaCollection {


    @BeforeClass
    public void beforeClass(){

        baseURI="https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void userToMap(){

        Response response=given().accept(ContentType.JSON)
                .when().get("https://demoqa.com/Account/v1/User/11");

        response.then().assertThat().statusCode(401);

        Map<String, Object> jsonMap=response.body().as(Map.class);

        System.out.println("jsonMap = " + jsonMap);

        //verify the message
        String message= (String) jsonMap.get("message");
        Assert.assertEquals(message,"User not authorized!");

        //verify the code
        String code= (String) jsonMap.get("code");
        Assert.assertEquals(code, "1200");
    }


    @Test
    public void allUserToMap(){

        Response response=given().accept(ContentType.JSON)
                .queryParam("pagesize", 50)
                .queryParam("page", 1)
                .get("/allusers/alluser");

        response.then().assertThat().statusCode(200);

        //we need to do-seriliaze Json response to java collection

        //birden fazla json body bulundurduğundan list of map yapmamız gerekiyor

        List<Map<String, Object>> allUserMap=response.body().as(List.class);

        // 2. kullanıcının adını yazdırıp assert edelim
        String name = (String) allUserMap.get(1).get("name");
        System.out.println("name = " + name);

        //birden fazla içerik old liste atabiliriz
        List<String> skills = (List<String>) allUserMap.get(0).get("skills");
        Assert.assertEquals(skills.get(0), "PHP");


        List<Map<String, Object>> experienceListMap= (List<Map<String, Object>>) allUserMap.get(0).get("experience");

        System.out.println("experienceListMap = " + experienceListMap);

        System.out.println("experienceListMap.get(1).get(job) = " + experienceListMap.get(1).get("job"));


    }

}
