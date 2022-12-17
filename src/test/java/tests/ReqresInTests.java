package tests;

import com.github.javafaker.Faker;
import models.InputUserRegistrationDto;
import models.RegistrationBodyPojoModel;
import models.UpdateBodyDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.Specs.*;


public class ReqresInTests extends TestBase {

    Faker faker = new Faker(new Locale("en"));


    @Test
    @DisplayName("Получение данных о существующем пользователе")
    @Tag("reqres_test")
    void getSingleUserPositive() {
        given()
                .spec(baseRequestSpec)
                .when()
                .get("/users/2")
                .then()
                .spec(logsInResponse)
                .spec(successfulResponse);
    }


    @Test
    @DisplayName("Попытка получения данных о не существующем в БД пользователе ")
    @Tag("reqres_test")
    void getSingleUserNegative() {
        given()
                .spec(baseRequestSpec)
                .when()
                .get("/users/23")
                .then()
                .spec(logsInResponse)
                .statusCode(404);
    }


    @Test
    @DisplayName("Успешная регистрация")
    @Tag("reqres_test")
    void registrationIsSuccessfulWithDtoModel() {

        InputUserRegistrationDto body = new InputUserRegistrationDto();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("pistol");

        InputUserRegistrationDto response = given()
                .spec(baseRequestSpec)
                .spec(registerRequestSpec)
                .body(body)
                .when()
                .post()
                .then()
                .spec(logsInResponse)
                .spec(successfulResponse)
                .extract().as(InputUserRegistrationDto.class);

    }


    @Test
    @DisplayName("Попытка регистрации без пароля - проверка ошибки")
    @Tag("reqres_test")
    void tryToRegistrationWithoutPasswordWithPojoModel() {

        String userEmail = faker.internet().emailAddress();

        RegistrationBodyPojoModel body = new RegistrationBodyPojoModel();
        body.setEmail(userEmail);

        given()
                .spec(baseRequestSpec)
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
        String password = faker.internet().password();

        RegistrationBodyPojoModel body = new RegistrationBodyPojoModel();
        body.setPassword(password);

        given()
                .spec(baseRequestSpec)
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
                .spec(baseRequestSpec)
                .when()
                .delete("/users/2")
                .then()
                .spec(logsInResponse)
                .statusCode(204);
    }

    @Test
    @DisplayName("Обновление данных пользователя")
    @Tag("reqres_test")
    void updateUserData() {

        String userName = faker.name().firstName();
        String userJob = faker.job().position();

        UpdateBodyDto body = new UpdateBodyDto();
        body.setName(userName);
        body.setJob(userJob);

        UpdateBodyDto response = given()
                .spec(baseRequestSpec)
                .body(body)
                .when()
                .put("/users/2")
                .then()
                .spec(logsInResponse)
                .spec(successfulResponse)
                .extract().as(UpdateBodyDto.class);

        assertThat(response.getName()).isEqualTo(userName);
        assertThat(response.getJob()).isEqualTo(userJob);


    }


}
