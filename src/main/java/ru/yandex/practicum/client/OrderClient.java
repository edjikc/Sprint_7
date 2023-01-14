package ru.yandex.practicum.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.pojo.Order;

import static io.restassured.RestAssured.given;
import static ru.yandex.practicum.generator.OrderGenerator.DEFAULT_LIMIT;

public class OrderClient {
    private static final String PATH = "/api/v1/orders";

    @Step("Отправка запроса на создание нового заказа по объекту {order}")
    public  Response createNewOrder(Order order) {
        return given()
                .body(order)
                .when()
                .post(PATH);
    }
    @Step("Отправка запроса на получения списка заказов")
    public  Response listOrder() {
        return given()
                .queryParam("limit", DEFAULT_LIMIT)
                .get(PATH);
    }
}
