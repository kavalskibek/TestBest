package org.example.tests;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import org.example.framework.ApiBaseTest;
import org.example.framework.ConfigLoader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TokenTest extends ApiBaseTest {


    @BeforeClass
    public void init() {
        RestAssured.baseURI = ConfigLoader.get("amadeus.base_uri");
    }

    @Test
    public  void fetchAmadeusToken() {

        Assert.assertNotNull(accessToken);


    }
}
