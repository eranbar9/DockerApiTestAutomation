import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UsersTests {
    public final static String BASE_URL = "https://jsonplaceholder.typicode.com";
    public final static boolean printDebug = false;

    @Before
    public void setup() {
        //given().get(BASE_URL).getBody().prettyPrint();
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void updateValidUser_phoneNumber() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("phone", "(254)954-1289");

        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .body(requestParams.toString());
        Response response = request.patch("/users/3");
        if (printDebug) {
            response.prettyPrint();
        }

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 200);

        int id = response.getBody().jsonPath().get("id");
        assertEquals(3, id);

        String phone = response.getBody().jsonPath().get("phone");
        assertEquals("(254)954-1289", phone);
    }

    @Test
    public void updateInvalidUser_phoneNumber() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("phone", "1234567");

        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .body(requestParams.toString());
        Response response = request.patch("/users/3");
        if (printDebug) {
            response.prettyPrint();
        }

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 400);

        int id = response.getBody().jsonPath().get("id");
        assertEquals(3, id);

        String phone = response.getBody().jsonPath().get("phone");
        assertNotEquals("1234567", phone);
    }

    @Test
    public void deleteValidUser() {
        Response response = given()
                .delete("/users/3");
        if (printDebug) {
            response.prettyPrint();
        }

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 200);

        response = given()
                .get("/users/3");
        if (printDebug) {
            response.prettyPrint();
        }

        statusCode = response.getStatusCode();
        assertEquals(statusCode, 404);
    }

    @Test
    public void createValidUser() {
        JSONObject requestParams = new JSONObject();
        //requestParams.put("id", 11);
        requestParams.put("name", "Yossi");
        requestParams.put("username", "Yossi88");
        requestParams.put("email", "Yossi@hello.com");

        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .body(requestParams.toString());
        Response response = request.post("/users");
        if (printDebug) {
            response.prettyPrint();
        }

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 201);

        int id = response.getBody().jsonPath().get("id");
        assertEquals(11, id);
    }

    @Test
    public void createInvalidUser_blankFields() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "Yossi");
        requestParams.put("username", "Yossi88");
        requestParams.put("email", "Yossi@hello.com");

        Iterator<String> keys = requestParams.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            requestParams.put(key, "");
            if (printDebug) {
                System.out.println(requestParams);
            }
            RequestSpecification request = given()
                    .contentType(ContentType.JSON)
                    .body(requestParams.toString());
            Response response = request.post("/users");
            if (printDebug) {
                response.prettyPrint();
            }

            requestParams.put("name", "Yossi");
            requestParams.put("username", "Yossi88");
            requestParams.put("email", "Yossi@hello.com");

            int statusCode = response.getStatusCode();
            assertEquals(statusCode, 400);
        }
    }

    @Test
    public void createInvalidUser_missingFields() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "Yossi");
        requestParams.put("username", "Yossi88");
        requestParams.put("email", "Yossi@hello.com");

        List<String> keys = new ArrayList<>(requestParams.keySet());
        for (String key : keys) {
            requestParams.remove(key);
            if (printDebug) {
                System.out.println(requestParams);
            }
            RequestSpecification request = given()
                    .contentType(ContentType.JSON)
                    .body(requestParams.toString());
            Response response = request.post("/users");
            if (printDebug) {
                response.prettyPrint();
            }

            requestParams.put("name", "Yossi");
            requestParams.put("username", "Yossi88");
            requestParams.put("email", "Yossi@hello.com");

            int statusCode = response.getStatusCode();
            assertEquals(statusCode, 400);
        }
    }

    @Test
    public void getValidUser() {
        Response response = given()
                .get("/users/5");
        if (printDebug) {
            response.prettyPrint();
        }

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 200);

        int id = response.getBody().jsonPath().get("id");
        assertEquals(5, id);
    }

    @Test
    public void getInvalidUser() {
        Response response = given()
                .get("/users/15");
        if (printDebug) {
            response.prettyPrint();
        }

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 404);
    }

    @Test
    public void getValidUsersList() {
        Response response = given()
                .get("/users");
        if (printDebug) {
            response.prettyPrint();
        }

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 200);
    }
}
