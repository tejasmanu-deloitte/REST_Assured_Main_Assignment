import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
import java.util.ArrayList;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;



public class UserOperation {
    ExtractData data = new ExtractData();
    ArrayList<JSONObject> arr;
    String tokenG;
    String userid;
    String login_path = "C:\\Users\\tejasmas\\Desktop\\Rest_Assured_Main_Assignment\\src\\test\\resources\\login.xlsx";

    @BeforeClass
    public void post_data() throws IOException {

        String register_path = "C:\\Users\\tejasmas\\Desktop\\Rest_Assured_Main_Assignment\\src\\test\\resources\\register.xlsx";
        arr = data.user_details(register_path);

        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com/user";

    }


    @Test(priority = 1)
    public void testPost()
    {
        for(JSONObject jsonData: arr) {

            System.out.println(jsonData);
            given()
                    .contentType("application/json")
                    .body(jsonData.toString())
                    .when()
                    .post("/register")
                    .then()
                    .statusCode(201);
        }
    }

    @Test(priority = 2)
    public void Authenticate() throws IOException {
        baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = RestAssured.given();

        String loginDetails = data.login_details(login_path);

        request.header("Content-Type", "application/json");
        Response responseG = request.body(loginDetails).post("/user/login");
        responseG.prettyPrint();
        String jsonString=responseG.getBody().asString();
        tokenG= JsonPath.from(jsonString).get("token");

        JSONObject obj = new JSONObject(jsonString);
        JSONObject puzzle = obj.getJSONObject("user");
        userid = puzzle.getString("_id");

        System.out.println(tokenG);
        System.out.println(userid);

    }

}
