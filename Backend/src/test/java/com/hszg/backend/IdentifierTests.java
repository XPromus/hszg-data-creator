package com.hszg.backend;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest (
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)

@DisplayName("Identifier Tests")
public class IdentifierTests {

    private static final String TEST_JSON = "[\n" +
            "        {\n" +
            "            \"id\": 0,\n" +
            "            \"title\": \"Start\",\n" +
            "            \"description\": \"Beschreibung 1\",\n" +
            "            \"type\": \"start\",\n" +
            "            \"oneGoal\": false,\n" +
            "            \"goal\": 0,\n" +
            "            \"options\": [\n" +
            "            {\n" +
            "                \"name\": \"Ja\",\n" +
            "                \"goal\": 1\n" +
            "            },\n" +
            "            {\n" +
            "                \"name\": \"Nein\",\n" +
            "                \"goal\": 2\n" +
            "            }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 1,\n" +
            "            \"title\": \"Ja Node\",\n" +
            "            \"description\": \"Beschreibung 2\",\n" +
            "            \"type\": \"normal\",\n" +
            "            \"oneGoal\": true,\n" +
            "            \"goal\": 3,\n" +
            "            \"options\": [\n" +
            "            {\n" +
            "                \"name\": \"Option 1\",\n" +
            "                \"goal\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"name\": \"Option 2\",\n" +
            "                \"goal\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"name\": \"Option 3\",\n" +
            "                \"goal\": 0\n" +
            "            }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 2,\n" +
            "            \"title\": \"Nein Node\",\n" +
            "            \"description\": \"Beschreibung 3\",\n" +
            "            \"type\": \"normal\",\n" +
            "            \"oneGoal\": true,\n" +
            "            \"goal\": 3,\n" +
            "            \"options\": [\n" +
            "            {\n" +
            "                \"name\": \"Option 1\",\n" +
            "                \"goal\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"name\": \"Option 2\",\n" +
            "                \"goal\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"name\": \"Option 3\",\n" +
            "                \"goal\": 0\n" +
            "            }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 3,\n" +
            "            \"title\": \"Ende\",\n" +
            "            \"description\": \"Beschreibung 4\",\n" +
            "            \"type\": \"end\",\n" +
            "            \"oneGoal\": true,\n" +
            "            \"goal\": 0,\n" +
            "            \"options\": [\n" +
            "            {\n" +
            "                \"name\": \"Option 1\",\n" +
            "                \"goal\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"name\": \"Option 2\",\n" +
            "                \"goal\": 0\n" +
            "            }\n" +
            "            ]\n" +
            "        }\n" +
            "    ]";

    @BeforeAll
    public static void init() {
        RestAssured.port = 8080;
    }

    @Test
    public void createIdentifierTest() {

        var body = new JSONObject();
        body.put("identifierName", "test");

        var response = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("api/v1/identifier/create");

        response.then().statusCode(200);

    }

}
