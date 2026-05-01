//Response Extraction(Base of chaining)

//Day2test Class - Advanced API Testing with Response Extraction and Chaining
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import java.util.HashMap;

public class Day2test {

    //Response Extraction - Retrieving specific fields from JSON response
    @Test
    public void extractResponse()
    {
        //Make GET request and store the response object for extraction
        Response res = given()
        .when()
        .get(BaseTest.BASE_URL + "/posts/1");

        //Extract individual fields from JSON response using jsonPath()
        int id = res.jsonPath().getInt("id");
        String title = res.jsonPath().getString("title");
        String body = res.jsonPath().getString("body");
        int userId = res.jsonPath().getInt("userId");

        //Print extracted values for verification
        System.out.println("ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Body: " + body);
        System.out.println("User ID: " + userId);


    }
    //Dynamic POST - Creating a new post with HashMap payload instead of JSON file
    @Test
    public void dynamicPost()
    {
        //Create HashMap payload with post data (dynamic payload generation)
        HashMap<String,Object> payload = new HashMap<>();
        payload.put("title", "Dynamic Post");
        payload.put("body", "Hello World from HashMap");
        payload.put("userId", 1);

        //Send POST request to create new post (use /posts endpoint, not /posts/1)
        given()
                .header("content-type", "application/json")
                .body(payload)
                .when()
                .post(BaseTest.BASE_URL + "/posts") //POST to /posts creates new resource, returns 201
                .then()
                .statusCode(201); //Expected status code for successful POST create operation
    }

    //Chaining Concept - Creating a resource and then using its ID in subsequent requests
    @Test
    public void chainingConcept()
    {
        //Step 1: Create a new post using POST request
        Response postRes = given()
                .header("Content-Type","application/json")
                .body("{ \"title\": \"Test\", \"body\": \"API\", \"userId\": 1 }")
                .when()
                .post(BaseTest.BASE_URL + "/posts");

        //Step 2: Extract the ID from the created post response
        int id = postRes.jsonPath().getInt("id");

        //Step 3: Use the extracted ID in a subsequent GET request (chaining)
        given()
                .when()
                .get(BaseTest.BASE_URL + "/posts/" + id)
                .then()
                .statusCode(404); // mock limitation - JSONPlaceholder returns 404 for newly created posts
    }




}
