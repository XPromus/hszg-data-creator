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

@SpringBootTest (
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class UserTests {

    @BeforeAll
    public static void init() {
        RestAssured.port = 8080;
    }

    @Test
    public void createEditorUser() throws ParseException {

        JSONObject body = new JSONObject();
        body.put("email", "test@mail.com");
        body.put("password", "password");

        var postResponse = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(body)
                .post("api/v1/user/create");

        postResponse.then().statusCode(200);
        var postContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(postResponse.body().asString());
        System.err.println(postContent);

    }

    @Test
    public void getUserByEmail() throws ParseException {

        JSONObject body = new JSONObject();
        body.put("email", "test@mail.com");
        body.put("password", "password");

        var postResponse = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(body)
                .post("api/v1/user/create");

        postResponse.then().statusCode(200);

        var getResponse = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.TEXT)
                .body(body.get("email"))
                .get("api/v1/user/get/email");

        getResponse.then().statusCode(200);
        var getContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(getResponse.body().asString());
        System.err.println(getContent);

    }

    @Test
    public void getUserById() throws ParseException {

        JSONObject body = new JSONObject();
        body.put("email", "test@mail.com");
        body.put("password", "password");

        var postResponse = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(body)
                .post("api/v1/user/create");

        postResponse.then().statusCode(200);
        var postContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(postResponse.body().asString());
        var id = postContent.getAsNumber("id").longValue();

        var getResponse = RestAssured.given()
                .accept(ContentType.JSON)
                .get("api/v1/user/get/" + id);

        getResponse.then().statusCode(200);
        var getContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(getResponse.body().asString());
        System.err.println(getContent);

    }

    @Test
    public void checkPermission() throws ParseException {

        JSONObject body = new JSONObject();
        body.put("email", "test@mail.com");
        body.put("password", "password");

        var postResponse = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(body)
                .post("api/v1/user/create");

        postResponse.then().statusCode(200);
        var postContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(postResponse.body().asString());
        var id = postContent.getAsNumber("id").longValue();

        var getResponse = RestAssured.given()
                .accept(ContentType.ANY)
                .contentType(ContentType.TEXT)
                .body("READ_DATA")
                .get("api/v1/user/permission/" + id);

        getResponse.then().statusCode(200);
        var getContent = getResponse.body().asString();
        var permission = Boolean.parseBoolean(getContent);
        Assertions.assertTrue(permission);

    }

    @Test
    public void editUser() throws ParseException {

        JSONObject body = new JSONObject();
        body.put("email", "test@mail.com");
        body.put("password", "password");

        JSONObject changes = new JSONObject();
        changes.put("newEmail", "new-mail@mail.com");
        changes.put("newPassword", "new-password");

        var postResponse = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(body)
                .post("api/v1/user/create");

        postResponse.then().statusCode(200);
        var postContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(postResponse.body().asString());
        var id = postContent.getAsNumber("id").longValue();

        var editResponse = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(changes)
                .post("api/v1/user/edit/" + id);

        editResponse.then().statusCode(200);
        var editContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(editResponse.body().asString());
        Assertions.assertEquals(changes.get("newEmail"), editContent.get("email"));
        Assertions.assertEquals(changes.get("newPassword"), editContent.get("password"));

    }

    @Test
    public void addPrivilegeToUser() throws ParseException {

        JSONObject body = new JSONObject();
        body.put("email", "test@mail.com");
        body.put("password", "password");

        var postResponse = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(body)
                .post("api/v1/user/create");

        postResponse.then().statusCode(200);
        var postContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(postResponse.body().asString());
        var id = postContent.getAsNumber("id").longValue();

        var newPrivilege = "WRITE_DATA";
        var addRequest = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.TEXT)
                .body(newPrivilege)
                .post("api/v1/user/add/privilege/" + id);

        addRequest.then().statusCode(200);
        var addContent = (JSONObject) new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE).parse(addRequest.body().asString());
        var privileges = (JSONArray) addContent.get("privileges");

        var found = false;
        for (Object o : privileges) {
            var privilege = (JSONObject) o;
            var name = privilege.get("name");

            if (name.equals(newPrivilege)) {
                found = true;
            }
        }

        Assertions.assertTrue(found);

    }

}
