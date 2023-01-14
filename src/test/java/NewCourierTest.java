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
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NewCourierTest {
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
    @DisplayName("Create new courier")
    public void createNewCourier() {
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        response.assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", Matchers.equalTo(true));

        id = loginResponse.extract().path("id");

    }

    @Test
    @DisplayName("Create identical couriers")
    public void courierAlreadyExists() {
        createNewCourier();
        courierClient.create(courier)
                //.assertThat()
                .statusCode(SC_CONFLICT)
                .body("message", Matchers.equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Create courier with existing login")
    public void courierLoginAlreadyExists() {
        createNewCourier();
        courierClient.create(CourierGenerator.getCourierWithName("asd"))
                .statusCode(SC_CONFLICT)
                .body("message", Matchers.equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Create courier no required field")
    public void courierInvalidField() {
        ValidatableResponse response = courierClient.create(CourierGenerator.getInvalid());
        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", Matchers.equalTo("Недостаточно данных для создания учетной записи"));

    }
}
