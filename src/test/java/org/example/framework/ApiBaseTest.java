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

        //fetching token once
        accessToken  = RestAssured
                .given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", ConfigLoader.get("amadeus.client_id"))
                .formParam("client_secret", ConfigLoader.get("amadeus.client_secret"))
                .when()
                .post(ConfigLoader.get("amadeus.token_path"))
                .then()
                .statusCode(200)
                .extract()
                .jsonPath().getString("access_token");


        //build a shared spec with auth header

        spec = new RequestSpecBuilder()
                .setBaseUri(ConfigLoader.get("amadeus.base_uri"))
                .addHeader("Authorization", "Bearer "+ accessToken)
                .build();

    }
}
