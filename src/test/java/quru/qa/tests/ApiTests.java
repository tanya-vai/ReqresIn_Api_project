package quru.qa.tests;

import org.junit.jupiter.api.Test;
import quru.qa.models.lombok.UserBodyLombokModel;
import quru.qa.models.pojo.RegistrationBodyPojoModel;
import quru.qa.models.pojo.RegistrationResponsePojoModel;
import quru.qa.models.pojo.UserBodyPojoModel;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static quru.qa.specs.CreationSpecs.creationRequestSpec;
import static quru.qa.specs.CreationSpecs.creationResponseSpec;
import static quru.qa.specs.RegistrationSpecs.registrationRequestSpec;
import static quru.qa.specs.RegistrationSpecs.registrationResponseSpec;

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
    void notFoundUserTest() {

        given()
                .when()
                .get("https://reqres.in/api/users/55")
                .then()
                .statusCode(404);
    }

    @Test
    void createUserTest() {

        UserBodyPojoModel body = new UserBodyPojoModel();
        body.setName("Tanya");
        body.setJob("QA");

        given()
                .spec(creationRequestSpec)
                .body(body)
                .when()
                .post()
                .then()
                .spec(creationResponseSpec)
                .body("name", is(body.getName()),
                        "job", is(body.getJob()));

    }
   @Test
    void createUserWithLombokTest() {

        UserBodyLombokModel body = new UserBodyLombokModel();
        body.setName("Tanya");
        body.setJob("QA");

        given()
                .spec(creationRequestSpec)
                .body(body)
                .when()
                .post()
                .then()
                .spec(creationResponseSpec)
                .body("name", is(body.getName()),
                        "job", is(body.getJob()));

    }

    @Test
    void userRegistrationTest() {

        RegistrationBodyPojoModel register = new RegistrationBodyPojoModel();
        register
                .setEmail("eve.holt@reqres.in")
                .setPassword("pistol");

        RegistrationResponsePojoModel response = given()
                .spec(registrationRequestSpec)
                .body(register)
                .when()
                .post()
                .then()
                .spec(registrationResponseSpec)
                .extract()
                .as(RegistrationResponsePojoModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
        assertThat(response.getId()).isEqualTo(4);
    }


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
