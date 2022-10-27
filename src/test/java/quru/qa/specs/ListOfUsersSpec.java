package quru.qa.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.notNullValue;
import static quru.qa.helpers.CustomApiListener.withCustomTemplates;

public class ListOfUsersSpec {


    public static RequestSpecification listOfUsersRequestSpec = with()
            .filter(withCustomTemplates())
            .baseUri("https://reqres.in")
            .basePath("api/users")
            .log().uri()
            .log().body()
            .contentType(ContentType.JSON);


    public static ResponseSpecification listOfUsersResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .build();

}
