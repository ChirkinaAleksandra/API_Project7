import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.services.praktikum.scooter.qa.Orders;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

@RunWith(Parameterized.class)
public class OrdersCreatingTests {


    private final Orders orders;

    public OrdersCreatingTests(Orders orders) {
        this.orders = orders;
    }

    private static final String GET_PATH = "/api/v1/orders";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}")
    public static Object[][] getTestData() {
        return new Object[][]{{new Orders("Милый", "Котик", "ул.Котовая 150","Спортивная", "89999999999", 3, "2023-04-12","У меня лапки", new String[]{"BLACK"})},
                {new Orders("Большой", "Хаски","ул.Собачковая 111", "Комсомольская", "89995000000", 5,"2023-04-14", "Хороший мальчик ждет самокат!", new String[]{"GREY"})},
                {new Orders("Елена", "Фрунся", "ул.Комсомола 234","Комсомольская", "89555001023", 7, "2023-04-11","", new String[]{"BLACK", "GREY"})},
                {new Orders("Анастасия", "Копылова","ул.Графская 2", "Спортивная", "89995008925", 3,"2023-04-11", "", new String[]{})},};
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа с различными данными")
    public void checkCreateOrder() {
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .body(orders)
                .when()
                .post(GET_PATH);
        response
                .then().log().all()
                .assertThat()
                .and()
                .statusCode(HttpStatus.SC_CREATED)
                .body("track", Matchers.notNullValue());
    }
}