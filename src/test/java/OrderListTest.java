import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.client.OrderClient;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static ru.yandex.practicum.generator.OrderGenerator.DEFAULT_LIMIT;

public class OrderListTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }


    @Test
    @DisplayName("Get oder list")
    public void getOrderList() {
        Response response = orderClient.listOrder();
        response.then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("orders", Matchers.notNullValue());

        List<Object> orders = response.getBody().jsonPath().getList("orders");

        Assert.assertEquals(DEFAULT_LIMIT, orders.size());
    }
}
