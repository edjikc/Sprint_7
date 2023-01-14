import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static ru.yandex.practicum.pojo.OrderGenerator.DEFAULT_LIMIT;

public class OrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }


    @Test
    @DisplayName("Get oder list")
    public void getOrderList() {
        Response response = given()
                .queryParam("limit", DEFAULT_LIMIT)
                .get("/api/v1/orders");
        response.then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("orders", Matchers.notNullValue());

        List<Object> orders = response.getBody().jsonPath().getList("orders");

        Assert.assertEquals(DEFAULT_LIMIT, orders.size());
    }
}
