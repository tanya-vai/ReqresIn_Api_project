package quru.qa.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import quru.qa.models.lombok.*;
import quru.qa.models.pojo.RegistrationBodyPojoModel;
import quru.qa.models.pojo.RegistrationResponsePojoModel;
import quru.qa.models.pojo.UserBodyPojoModel;

import java.io.File;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import static quru.qa.specs.Specs.*;

public class ApiTests {

    File user = new File("src/test/resources/user.json");

    @Test
    @DisplayName("Check user registration")
    void checkUserRegistrationTest() {

        RegistrationBodyPojoModel register = new RegistrationBodyPojoModel();
        register
                .setEmail("eve.holt@reqres.in")
                .setPassword("pistol");

        RegistrationResponsePojoModel response =  step("User registration", () ->
                given().spec(requestSpec)
                .body(register)
                .when()
                .post("/api/register")
                .then()
                .spec(responseSpecToken)
                .extract()
                .as(RegistrationResponsePojoModel.class));

        step("Check that user is registered", () -> {
        assertThat(response.getToken()).isNotNull();
        assertThat(response.getId()).isNotNull();
        });
    }
    @Test
    @DisplayName("Check user authorization")
    void checkUserAuthTest() {
        LoginBodyLombokModel login = new  LoginBodyLombokModel();
        login.setEmail("eve.holt@reqres.in");
        login.setPassword("pistol");

        LoginResponseLombokModel response = step("User Authorisation", () ->
                given().spec(requestSpec)
                        .body(login)
                        .when()
                        .post("/api/login")
                        .then()
                        .spec(responseSpecToken)
                        .statusCode(200)
                        .extract()
                        .as(LoginResponseLombokModel.class));

        step("Check that user is authorised", () ->
                assertThat(response.getToken()).isNotNull());
    }

    @Test
    @DisplayName("Check User Data")
    void checkGetSingleUserTest() {
        SingleResponseLombokModel response = step("Get user data", () ->
                given().spec(requestSpec)
                        .when()
                        .get("/api/users/6")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract()
                        .as(SingleResponseLombokModel.class));

        step("Проверка, что данные пользователя корректные", () -> {
            assertThat(response.getData().getId()).isEqualTo(6);
            assertThat(response.getData().getEmail()).isEqualTo("tracey.ramos@reqres.in");
            assertThat(response.getData().getFirstName()).isEqualTo("Tracey");
            assertThat(response.getData().getLastName()).isEqualTo("Ramos");
            assertThat(response.getData().getAvatar())
                    .isEqualTo("https://reqres.in/img/faces/6-image.jpg");
            assertThat(response.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
            assertThat(response.getSupport().getText())
                    .isEqualTo("To keep ReqRes free, contributions towards server costs are appreciated!");
        });
    }

    @Test
    @DisplayName("Check user creation")
    void checkUserCreationTest() {
        UserBodyLombokModel user = new UserBodyLombokModel();
                user.setName("Tanya");
                user.setJob("QA");

        userResponseModel response = step("Create a new user ", () ->
                given().spec(requestSpec)
                        .body(user)
                        .when()
                        .post("/api/users")
                        .then()
                        .spec(responseSpec)
                        .statusCode(201)
                        .extract()
                        .as(userResponseModel.class));

        step("Check that data of created user is correct", () -> {
            assertThat(response.getName()).isEqualTo("Tanya");
            assertThat(response.getJob()).isEqualTo("QA");
        });
    }

    @Test
    @DisplayName("Check that user is updated correctly")
    void checkUserUpdtaedTest() {
        UserBodyLombokModel user = new UserBodyLombokModel();
        user.setName("Tanya");
        user.setJob("AQA");

        userResponseModel response = step("Редактирование имени и работы пользователя", () ->
                given().spec(requestSpec)
                        .body(user)
                        .when()
                        .put("/api/users/3")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract()
                        .as(userResponseModel.class));

        step("Check that user data is updated", () -> {
            assertThat(response.getName()).isEqualTo("Tanya");
            assertThat(response.getJob()).isEqualTo("AQA");
        });
    }

    @Test
    @DisplayName(" Single user 'Not Found' ")
    void notFoundUserTest() {

        step("Check that that searched user isn't found", () ->
        given().spec(requestSpec)
                .when()
                .get("/api/users/55")
                .then()
                .spec(responseSpec)
                .statusCode(404));
    }


//    @Test
//    void createUserTest() {
//
//        UserBodyPojoModel body = new UserBodyPojoModel();
//        body.setName("Tanya");
//        body.setJob("QA");
//
//        given()
//                .spec(creationRequestSpec)
//                .body(body)
//                .when()
//                .post()
//                .then()
//                .spec(creationResponseSpec)
//                .body("name", is(body.getName()),
//                        "job", is(body.getJob()));
//
//    }
//   @Test
//    void createUserWithLombokTest() {
//
//        UserBodyLombokModel body = new UserBodyLombokModel();
//        body.setName("Tanya");
//        body.setJob("QA");
//
//        given()
//                .spec(creationRequestSpec)
//                .body(body)
//                .when()
//                .post()
//                .then()
//                .spec(creationResponseSpec)
//                .body("name", is(body.getName()),
//                        "job", is(body.getJob()));
//
//    }


//    @Test
//    void userRegistrationWithLombokTest() {
//
//        RegistrationBodyLombokModel register = new RegistrationBodyLombokModel();
//        register.setEmail("eve.holt@reqres.in");
//        register.setPassword("pistol");
//
//        RegistrationResponsePojoModel response = given()
//                .spec(registrationRequestSpec)
//                .body(register)
//                .when()
//                .post()
//                .then()
//                .spec(registrationResponseSpec)
//                .extract()
//                .as(RegistrationResponsePojoModel.class);
//
//        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
//        assertThat(response.getId()).isEqualTo(4);
//    }


    @Test
    void updateUserJobTest() {

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
