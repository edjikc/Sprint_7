import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.client.OrderClient;
import ru.yandex.practicum.generator.OrderGenerator;
import ru.yandex.practicum.pojo.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;

@RunWith(Parameterized.class)
public class MakeOrderTest {
    private final Order order;
    private String track;
    private OrderClient orderClient;

    public MakeOrderTest(Order order) {
        this.order = order;
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @After
    public void cleanUp() {
        given()
                .body(track)
                .when()
                .post("/api/v1/orders/cancel");
    }

    @Parameterized.Parameters
    public static Object[] getTextData() {
        return new Object[] {
                OrderGenerator.getBlack(),
                OrderGenerator.getBlackAndGrey(),
                OrderGenerator.getWithoutColour()
        };
    }

    @Test
    @DisplayName("Create orders with different colours")
    public void createOrder() {
        Response response = orderClient.createNewOrder(order);

        response.then()
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("track", Matchers.notNullValue());

         track = response.getBody().jsonPath().getString("track");

    }

}
