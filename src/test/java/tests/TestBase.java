package tests;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
        Configuration.baseUrl = "https://reqres.in/api";
    }
}
