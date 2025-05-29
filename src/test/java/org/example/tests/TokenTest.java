package org.example.tests;

import io.restassured.RestAssured;

import org.example.framework.ApiBaseTest;
import org.example.framework.ConfigLoader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class TokenTest extends ApiBaseTest {



    @Test
    public  void fetchAmadeusToken() {

        Assert.assertNotNull(accessToken);


    }
}
