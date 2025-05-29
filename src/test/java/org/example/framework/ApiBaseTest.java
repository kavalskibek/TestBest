package org.example.framework;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeSuite;

public class ApiBaseTest {
    protected static String accessToken;
    protected static RequestSpecification spec;

    @BeforeSuite
    public void initApi() {
        // 1) set the base URI
        RestAssured.baseURI = ConfigLoader.get("amadeus.base_uri");
        // 2) enable logging on failures
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        // 3) fetch the token
        accessToken = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type",    "client_credentials")
                .formParam("client_id",     ConfigLoader.get("amadeus.client_id"))
                .formParam("client_secret", ConfigLoader.get("amadeus.client_secret"))
                .log().all()
                .when()
                .post(ConfigLoader.get("amadeus.token_path"))
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath().getString("access_token");

        // 4) build a shared RequestSpecification for later tests
        spec = new RequestSpecBuilder()
                .setBaseUri(RestAssured.baseURI)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
    }
}
