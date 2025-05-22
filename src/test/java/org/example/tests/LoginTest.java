package org.example.tests;

import org.example.framework.BaseTest;               // ① extend BaseTest
import org.example.pages.LoginPage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class LoginTest extends BaseTest {            // ← extends BaseTest

    @DataProvider(name = "loginData")
    public Object[][] loginData() throws IOException {
        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("loginData.json")) {
            if (is == null) {
                throw new FileNotFoundException("loginData.json not found on classpath");
            }
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            JSONArray arr = new JSONArray(json);
            Object[][] data = new Object[arr.length()][2];
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                data[i][0] = o.getString("username");
                data[i][1] = o.getString("password");
            }
            return data;
        }
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password) {
        // ② use the driver instance set up by BaseTest
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        // TODO: add assertions here, e.g. check URL or a visible element
    }
}
