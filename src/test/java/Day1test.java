import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.io.File;

//Basic REST API Testing with RestAssured - CRUD Operations on Posts
public class Day1test {

    //GET Request - Retrieving a single post by ID
    @Test
    public void getPost()
    {
            given()
            .when()
            .get(BaseTest.BASE_URL + "/posts/1")
            .then()
            .statusCode(200)
            .body("userId", equalTo(1))
            .body("id", equalTo(1))
            .body("title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"))
            .body("body", equalTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));

    }
    //POST Request - Creating a new post with JSON payload from file
    @Test
    public void createPost()
    {
        File payload = new File("src/test/resources/payloads/createPosts.json");
        given()
                .header("Content-type", "application/json")
                .body(payload)
                .when()
                .post(BaseTest.BASE_URL + "/posts")
                .then()
                .statusCode(201)
                .body("title",equalTo("Test Post"))
                .and()
                .body("userId",equalTo(1));

    }
    //PUT Request - Updating an existing post completely
    @Test
    public void updatePost()
    {
        File payload = new File("src/test/resources/payloads/updatePosts.json");
        given()
                .header("Content-type", "application/json")
                .body(payload)
                .when()
                .put(BaseTest.BASE_URL + "/posts/1")
                .then()
                .statusCode(200)
                .body("title",equalTo("Test Post Updated"))
                .and()
                .body("body",equalTo("This is an updated test post created for testing purposes."));
    }

    //PATCH Request - Partially updating a post
    @Test
    public void patchPost()
    {
        File payload = new File("src/test/resources/payloads/patchPosts.json");
        given()
                .header("Content-type", "application/json")
                .body(payload)
                .when()
                .patch(BaseTest.BASE_URL + "/posts/1")
                .then()
                .statusCode(200)
                .body("title",equalTo("Test Post Patched"))
                .and()
                .body("body",equalTo("This is a patched test post created for testing purposes."));
    }

    //DELETE Request - Deleting a post by ID
    @Test
    public void deletePost()
    {
        given()
                .when()
                .delete(BaseTest.BASE_URL + "/posts/1")
                .then()
                .statusCode(200);
    }

}
