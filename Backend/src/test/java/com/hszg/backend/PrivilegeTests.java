package com.hszg.backend;

import com.hszg.backend.data.privileges.Privileges;
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

import java.nio.charset.StandardCharsets;

@SpringBootTest (
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class PrivilegeTests {

    @BeforeAll
    public static void init() {
        RestAssured.port = 8080;
    }

    @Test
    public void getAllPrivileges() throws ParseException {

        var privileges = Privileges.getAllPrivileges();
        var getResponse = RestAssured.given()
                .accept(ContentType.JSON)
                .get("api/v1/privilege/all");

        getResponse.then().statusCode(200);
        var getContent = (JSONArray) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(getResponse.body().asString());

        for (String expectedPrivilege : privileges) {

            var found = false;
            for (Object o : getContent) {
                var privilege = (JSONObject) o;
                var name = (String) privilege.get("name");

                if (expectedPrivilege.equals(name)) {
                    found = true;
                    break;
                }
            }

            Assertions.assertTrue(found);

        }

    }

    @Test
    public void getPrivilegeByName() throws ParseException {

        var privilege = Privileges.EDIT_PAGE_PERMISSION;
        var getResponse = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.TEXT)
                .body(privilege)
                .get("api/v1/privilege/get/name");

        getResponse.then().statusCode(200);
        var getContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(getResponse.body().asString());

        Assertions.assertEquals(privilege, getContent.get("name"));

    }

    @Test
    public void getPrivilegeById() throws ParseException {

        var privilege = Privileges.EDIT_PAGE_PERMISSION;
        var getResponse = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.TEXT)
                .body(privilege)
                .get("api/v1/privilege/get/name");

        getResponse.then().statusCode(200);
        var getContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(getResponse.body().asString());
        var id = getContent.getAsNumber("id").longValue();

        var getIdResponse = RestAssured.given()
                .accept(ContentType.JSON)
                .get("api/v1/privilege/get/" + id);

        getIdResponse.then().statusCode(200);
        var getIdContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(getIdResponse.body().asString());

        Assertions.assertEquals(privilege, getIdContent.get("name"));

    }

}
