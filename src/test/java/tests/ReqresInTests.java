package tests;

import com.github.javafaker.Faker;
import models.RegistrationBodyLombokModel;
import models.RegistrationBodyPojoModel;
import models.UpdateBodyLombokModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.Specs.*;


public class ReqresInTests {

    Faker faker = new Faker(new Locale("en"));

    String userEmail,
            password,
            name,
            job;


    @BeforeEach
    void prepareTestData() {
        userEmail = faker.internet().emailAddress();
        password = faker.internet().password();
        name = faker.name().firstName();
        job = faker.job().position();
    }



    @Test
    @DisplayName("Получение данных о существующем пользователе")
    @Tag("reqres_test")
    void getSingleUserPositive() {
        given()
                .spec(userRequestSpec)
                .when()
                .get("/2")
                .then()
                .spec(logsInResponse)
                .spec(successfulResponse);
    }



    @Test
    @DisplayName("Попытка получения данных о не существующем в БД пользователе ")
    @Tag("reqres_test")
    void getSingleUserNegative() {
        given()
                .spec(userRequestSpec)
                .when()
                .get("/23")
                .then()
                .spec(logsInResponse)
                .statusCode(404);
    }


    @Test
    @DisplayName("Успешная регистрация")
    @Tag("reqres_test")
    void registrationIsSuccessfulWithLombok() {

        RegistrationBodyLombokModel body = new RegistrationBodyLombokModel();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("pistol");

        RegistrationBodyLombokModel response = given()
                .spec(registerRequestSpec)
                .body(body)
                .when()
                .post()
                .then()
                .spec(logsInResponse)
                .spec(successfulResponse)
                .extract().as(RegistrationBodyLombokModel.class);

    }


    @Test
    @DisplayName("Попытка регистрации без пароля - проверка ошибки")
    @Tag("reqres_test")
    void tryToRegistrationWithoutPasswordWithPojoModel() {

        RegistrationBodyPojoModel body = new RegistrationBodyPojoModel();
        body.setEmail(userEmail);

        given()
                .spec(registerRequestSpec)
                .body(body)
                .when()
                .post()
                .then()
                .spec(logsInResponse)
                .spec(badResponse);
    }


    @Test
    @DisplayName("Попытка регистрации без email - проверка ошибки")
    @Tag("reqres_test")
    void tryToRegistrationWithoutEmailWithPojoModel() {
        RegistrationBodyPojoModel body = new RegistrationBodyPojoModel();
        body.setPassword(password);

        given()
                .spec(registerRequestSpec)
                .body(body)
                .when()
                .post()
                .then()
                .spec(logsInResponse)
                .spec(badResponse);
    }


    @Test
    @DisplayName("Удаление пользователя")
    @Tag("reqres_test")
    void deleteSuccessful() {
        given()
                .spec(userRequestSpec)
                .when()
                .delete("/2")
                .then()
                .spec(logsInResponse)
                .statusCode(204);
    }

    @Test
    @DisplayName("Обновление данных пользователя")
    @Tag("reqres_test")
    void updateUserData() {
        UpdateBodyLombokModel body = new UpdateBodyLombokModel();
        body.setName(name);
        body.setJob(job);


        UpdateBodyLombokModel response = given()
                .spec(userRequestSpec)
                .body(body)
                .when()
                .put("/23")
                .then()
                .spec(logsInResponse)
                .spec(successfulResponse)
                .extract().as(UpdateBodyLombokModel.class);

        assertThat(response).isNotNull();

    }


}
