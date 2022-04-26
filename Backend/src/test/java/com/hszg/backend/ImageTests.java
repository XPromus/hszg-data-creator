package com.hszg.backend;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import net.minidev.json.parser.ParseException;

import java.util.ArrayList;

@SpringBootTest (
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)

public class ImageTests {

    @BeforeAll
    public static void init() {
        RestAssured.port = 8080;
    }

    @Test
    public void getAllImagesTest() throws ParseException {

        var objectId = createObject();

        var images = 3;
        var imageIdList = new ArrayList<>();
        for (var i = 0; i < images; i++) {
            imageIdList.add(createImage(objectId));
        }

        var getResponse = RestAssured.given()
                .when()
                .get("api/v1/image/get/all");

        getResponse.then().statusCode(200);

        var getContent = (JSONArray) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(getResponse.body().asString());
        var yearNum = getContent.size();

        if (yearNum > 0) {
            Assertions.assertTrue(true);
        } else {
            Assertions.fail();
        }

    }

    @Test
    public void createYearTest() throws ParseException {

        var objectId = createObject();
        var imageId = createImage(objectId);

        var getResponse = RestAssured.given()
                .when()
                .get("api/v1/image/get/" + imageId);

        getResponse.then().statusCode(200);

    }

    @Test
    public void getImageByIdTest() throws ParseException {

        var objectId = createObject();
        var imageId = createImage(objectId);

        var getResponse = RestAssured.given()
                .when()
                .get("api/v1/image/get/" + imageId);

        getResponse.then().statusCode(200);

        var getContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(getResponse.body().asString());
        var getImageId = getContent.getAsNumber("id").longValue();

        Assertions.assertEquals(imageId, getImageId);

    }

    @Test
    public void getImagesFromObjectByIdTest() throws ParseException {

        var imageNum = 2;
        var object = createObject();
        ArrayList<Long> images = new ArrayList<>();

        for (var i = 0; i < imageNum; i++) {
            images.add(createImage(object));
        }

        var getResponse = RestAssured.given()
                .when()
                .get("api/v1/image/get/object/" + object);

        getResponse.then().statusCode(200);

        var getContent = (JSONArray) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(getResponse.body().asString());
        for (var i = 0; i < getContent.size(); i++) {
            var year = (JSONObject) getContent.get(i);
            var id = year.getAsNumber("id").longValue();
            Assertions.assertEquals(id, images.get(i));
        }

    }

    @Test
    public void deleteImageByIdTest() throws ParseException {

        var object = createObject();
        var image = createImage(object);

        RestAssured.given()
                .when()
                .delete("api/v1/image/delete/" + image)
                .then()
                .statusCode(200);

        var getResponse = RestAssured.given()
                .when()
                .get("api/v1/image/get/" + image);

        getResponse.then().statusCode(404);

    }

    @Test
    public void editImageTest() throws ParseException {

        var object = createObject();
        var image = createImage(object);

        var changes = new JSONObject();
        changes.put("newImagePath", "testPath");
        changes.put("newImageDescription", "This is a test description");

        var postResponse = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(changes)
                .when()
                .post("api/v1/image/edit/" + image);

        postResponse.then().statusCode(200);

        var getResponse = RestAssured.given()
                .when()
                .get("api/v1/image/get/" + image);

        getResponse.then().statusCode(200);

        var getContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(getResponse.body().asString());
        Assertions.assertEquals(changes.get("newImagePath"), getContent.get("imagePath"));
        Assertions.assertEquals(changes.get("newImageDescription"), getContent.get("imageDescription"));

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

    private Long createImage(Long objectId) throws ParseException {

        var postURL = "api/v1/image/create/" + objectId;
        var postResponse = RestAssured.given()
                .when()
                .post(postURL);

        postResponse.then().statusCode(200);

        var postContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(postResponse.body().asString());
        return postContent.getAsNumber("id").longValue();

    }

}
