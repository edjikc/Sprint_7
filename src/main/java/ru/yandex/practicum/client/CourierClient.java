package ru.yandex.practicum.client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.pojo.Client;
import ru.yandex.practicum.pojo.Courier;
import ru.yandex.practicum.pojo.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {
    private static final String PATH = "/api/v1/courier/";
    private static final String LOGIN = "/api/v1/courier/login";

    @Step("Отправка запроса на создание курьера по объекту {courier}")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then();
    }
    @Step("Логин курьера по {credentials}")
    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(LOGIN)
                .then();
    }
    @Step("Отправка запроса на удаление курьера по {id}")
    public ValidatableResponse delete(int id) {
        return  given()
                .spec(getSpec())
                .when()
                .delete(PATH + id)
                .then();
    }
}
