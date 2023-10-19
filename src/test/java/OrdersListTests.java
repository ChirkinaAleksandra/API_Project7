import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class OrdersListTests {
    private static final String GET_PATH = "/api/v1/orders";

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Список заказов")
    @Description("Проверка получения списка заказов для /api/v1/orders")
    public void getListOrdersTest() {
        given()
                .header("Content-type", "application/json").log().all()
                .get(GET_PATH)
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }
}