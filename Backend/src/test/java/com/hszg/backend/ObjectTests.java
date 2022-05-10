package com.hszg.backend;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest (
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)

@DisplayName("Object Tests")
public class ObjectTests {

    @BeforeAll
    public static void init() {
        RestAssured.port = 8080;
    }

    @Test
    public void createObjectTest() throws ParseException {

        var body = new JSONObject();
        body.put("name", "House 1");

        var response = RestAssured.given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(body)
            .when()
            .post("api/v1/object/create");

        var rawContent = response.body().asString();
        var jsonBody = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(rawContent);
        System.err.println(jsonBody);
        var id = jsonBody.get("id");

        response.then().statusCode(200);

        var getResponse = RestAssured.given()
                .when()
                .get("api/v1/object/id/" + id);

        getResponse.then().statusCode(200);

    }

    @Test
    public void getObjectTest() throws ParseException {

        var postBody = new JSONObject();
        postBody.put("name", "House 1");

        var postResponse = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(postBody)
                .when()
                .post("api/v1/object/create");

        var postContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(postResponse.body().asString());
        var postId = postContent.get("id");

        postResponse.then().statusCode(200);

        var getResponse = RestAssured.given()
                .when()
                .get("api/v1/object/id/" + postId);

        getResponse.then().statusCode(200);

        var getContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(getResponse.body().asString());
        var getId = getContent.get("id");

        Assertions.assertEquals(postId, getId);

    }

    @Test
    public void getObjectsTest() throws ParseException {

        var numberOfObjects = 3;
        ArrayList<Integer> idList = new ArrayList<>();

        for (var i = 0; i < numberOfObjects; i++) {

            var postBody = new JSONObject();
            postBody.put("name", "House " + i);

            var postResponse = RestAssured.given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(postBody)
                    .when()
                    .post("api/v1/object/create");

            var postContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(postResponse.body().asString());
            idList.add((int) postContent.get("id"));

        }

        var getResponse = RestAssured.given()
                .when()
                .get("api/v1/object/all");

        getResponse.then().statusCode(200);
        var body = getResponse.body().asString();
        System.out.println(body);

        JSONArray jsonContent = (JSONArray) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(body);

        for (var i = 0; i < jsonContent.size(); i++) {

            var object = (JSONObject) jsonContent.get(i);
            var id = object.get("id");

            Assertions.assertEquals(idList.get(i), id);

        }

    }

    @Test
    public void deleteObjectTest() throws ParseException {

        var body = new JSONObject();
        body.put("name", "House 1");

        var response = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("api/v1/object/create");

        var rawContent = response.body().asString();
        var jsonBody = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(rawContent);
        var id = jsonBody.get("id");

        var deleteResponse = RestAssured.given()
                .when()
                .delete("api/v1/object/delete/" + id);

        deleteResponse.then().statusCode(200);

        var getResponse = RestAssured.given()
                .when()
                .get("api/v1/object/id/" + id);

        getResponse.then().statusCode(404);

    }

    @Test
    public void editObjectTest() throws ParseException {

        var object = createObject();
        var changes = new JSONObject();
        changes.put("newName", "Test House");
        changes.put("newLatitude", 1);
        changes.put("newLongitude", 1);

        var postResponse = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(changes)
                .when()
                .post("api/v1/object/edit/" + object);

        postResponse.then().statusCode(200);

    }

    private Long createObject() throws ParseException {

        var body = new JSONObject();
        body.put("name", "House 1");

        var response = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("api/v1/object/create");

        var jsonBody = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(response.body().asString());
        return jsonBody.getAsNumber("id").longValue();

    }

}
