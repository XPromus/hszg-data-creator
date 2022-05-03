package com.hszg.backend;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest (
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)

public class YearTests {

    @BeforeAll
    public static void init() {
        RestAssured.port = 8080;
    }

    @Test
    public void getAllYears() throws ParseException {

        var objectID = createObject();

        var years = 3;
        ArrayList<Long> yearIdList = new ArrayList<>();

        for (var i = 0; i < years; i++) {
            yearIdList.add(createYear(objectID));
        }

        var getResponse = RestAssured.given()
                .when()
                .get("api/v1/year/get/all");

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

        var objectID = createObject();
        var postYearId = createYear(objectID);

        var getResponse = RestAssured.given()
                .when()
                .get("api/v1/year/get/" + postYearId);

        getResponse.then().statusCode(200);

    }

    @Test
    public void getYearByIdTest() throws ParseException {

        var objectID = createObject();
        var postYearId = createYear(objectID);

        var getResponse = RestAssured.given()
                .when()
                .get("api/v1/year/get/" + postYearId);

        getResponse.then().statusCode(200);

        var getContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(getResponse.body().asString());
        var getYearId = getContent.getAsNumber("id").longValue();

        Assertions.assertEquals(postYearId, getYearId);

    }

    @Test
    public void getYearsFromObjectByIdTest() throws ParseException {

        var yearNum = 2;
        var object = createObject();
        ArrayList<Long> years = new ArrayList<>();

        for (var i = 0; i < yearNum; i++) {
            years.add(createYear(object));
        }

        var getResponse = RestAssured.given()
                .when()
                .get("api/v1/year/get/object/" + object);

        getResponse.then().statusCode(200);

        var getContent = (JSONArray) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(getResponse.body().asString());

        for (var i = 0; i < getContent.size(); i++) {

            var year = (JSONObject) getContent.get(i);
            var id = year.getAsNumber("id").longValue();

            Assertions.assertEquals(id, years.get(i));

        }

    }

    @Test
    public void deleteYearByIdTest() throws ParseException {

        var object = createObject();
        var year = createYear(object);

        RestAssured.given()
                .when()
                .delete("api/v1/year/delete/" + year)
                .then()
                .statusCode(200);

        var getResponse = RestAssured.given()
                .when()
                .get("api/v1/year/get/" + year);

        getResponse.then().statusCode(404);

    }

    @Test
    public void editYearTest() throws ParseException {

        var object = createObject();
        var year = createYear(object);

        var changes = new JSONObject();
        changes.put("newYear", "2022-04-04");
        changes.put("newObjectName", "Changed Name");
        changes.put("newExists", "true");

        var postResponse = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(changes)
                .when()
                .post("api/v1/year/edit/" + year);

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

    private Long createYear(Long objectID) throws ParseException {

        var year = "2000-07-30";
        var postURL = "api/v1/year/create/" + objectID + "/" + year;

        var postResponse = RestAssured.given()
                .when()
                .post(postURL);

        postResponse.then().statusCode(200);

        var postContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(postResponse.body().asString());
        return postContent.getAsNumber("id").longValue();

    }

}
