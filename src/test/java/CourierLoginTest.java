import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.yandex.practicum.pojo.Courier;
import ru.yandex.practicum.pojo.CourierClient;
import ru.yandex.practicum.pojo.CourierCredentials;
import ru.yandex.practicum.pojo.CourierGenerator;

import static org.apache.http.HttpStatus.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CourierLoginTest {

    private Courier courier;
    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp() {
        courier = CourierGenerator.getDefault();
        courierClient = new CourierClient();
    }

    @After
    public void cleanUp() {
        courierClient.delete(id);
    }

    @Test
    @DisplayName("Courier authorization")
    public void loginCourier() {
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        loginResponse.assertThat()
                .statusCode(SC_OK)
                .and()
                .body("id", Matchers.notNullValue());
        id = loginResponse.extract().path("id");
    }

    @Test
    @DisplayName("Authorization with wrong login")
    public void invalidPassCourier() {
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(CourierGenerator.getDefaultIncorrectPassword()));
        loginResponse.assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", Matchers.equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Authorization without login")
    public void loginInvalidFields() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(CourierGenerator.getInvalid()));
        loginResponse.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", Matchers.equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Authorization of a non-existent courier")
    public void nonExistentCourierLogin() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(CourierGenerator.getCourierRandomLogin()));
        loginResponse.assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", Matchers.equalTo("Учетная запись не найдена"));
    }
}
