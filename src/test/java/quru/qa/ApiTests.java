package quru.qa;

import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ApiTests {

    File user = new File("src/test/resources/user.json");

    @Test
    void getNameOfUserTest() {

        given()
                .when()
                .get("https://reqres.in/api/users/6")
                .then()
                .log().body()
                .statusCode(200)
                .body("data.first_name", is("Tracey"));
    }

    @Test
    void notFoundUserTest(){

        given()
                .when()
                .get("https://reqres.in/api/users/55")
                .then()
                .statusCode(404);
    }

    @Test
    void createUserTest(){


        given()
                .contentType(JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().body()
                .statusCode(201)
                .body("name", is("Tanya"));

    }

    @Test
    void userRegistrationTest(){

        File registrationData = new File("src/test/resources/registration.json");

        given()
                .contentType(JSON)
                .body(registrationData)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));

    }


    @Test
    void updateUserJobTest(){

        given()
                .contentType(JSON)
                .body(user)
                .when()
                .put("https://reqres.in/api/user/3")
                .then()
                .log().body()
                .statusCode(200)
                .body("job", is("QA"));

    }




}
