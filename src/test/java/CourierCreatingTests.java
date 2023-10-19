import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import ru.services.praktikum.scooter.qa.Courier;
import ru.services.praktikum.scooter.qa.CourierClient;


public class CourierCreatingTests {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Создание учетной записи курьера")
    @Description("Проверка состояние кода и значений для полей /api/v1/courier")
    public void createCourierTest() {
        CourierClient courierClient = new CourierClient();
        String login = RandomStringUtils.randomAlphanumeric(2,15);
        String password = RandomStringUtils.randomAlphanumeric(7,15);
        String firstName = RandomStringUtils.randomAlphabetic(2,18);
        Response postRequestCreateCourier = courierClient.getPostRequestCreateCourier(new Courier(login, password, firstName));
        postRequestCreateCourier
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("ok", Matchers.is(true));
    }

    @Test
    @DisplayName("Создание курьера без имени курьера")
    @Description("Проверка состояние кода и сообщение при создании курьера без имени курьера")
    public void creatingCourierWithoutFirstName() {
        CourierClient courierClient = new CourierClient();
        String login = RandomStringUtils.randomAlphanumeric(2,15);
        String password = RandomStringUtils.randomAlphanumeric(7,15);
        Response postRequestCreateCourier = courierClient.getPostRequestCreateCourier(new Courier(login, password));
        postRequestCreateCourier
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("ok", Matchers.is(true));
    }

    @Test
    @DisplayName("Создание курьеров с одинаковыми логинами")
    @Description("Проверка состояние кода и сообщение при создании двух курьеров с одинаковыми логинами")
    public void creatingTwoIdenticalLoginCouriers() {
        CourierClient courierClient = new CourierClient();
        Response postRequestCreateCourier = courierClient.getPostRequestCreateCourier(new Courier("AlexSmith123", "7890", "alex"));
        postRequestCreateCourier
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CONFLICT)
                .and()
                .body("message", Matchers.notNullValue());
    }


    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка состояние кода и сообщение при создании курьера без логина")
    public void creatingCourierWithoutLogin() {
        CourierClient courierClient = new CourierClient();
        String password = RandomStringUtils.randomAlphanumeric(7,15);
        String firstName = RandomStringUtils.randomAlphabetic(2,18);
        Response postRequestCreateCourier = courierClient.getPostRequestCreateCourier(new Courier("", password, firstName));
        postRequestCreateCourier
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", Matchers.is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка состояние кода и сообщение при создании курьера без пароля")
    public void creatingCourierWithoutPassword() {
        CourierClient courierClient = new CourierClient();
        String login = RandomStringUtils.randomAlphanumeric(2,15);
        String firstName = RandomStringUtils.randomAlphabetic(2,18);
        Response postRequestCreateCourier = courierClient.getPostRequestCreateCourier(new Courier(login, "", firstName));
        postRequestCreateCourier
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", Matchers.is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без логина и пароля")
    @Description("Проверка состояние кода и сообщение при создании курьера без логина и пароля")
    public void creatingCourierWithoutLoginAndPassword() {
        CourierClient courierClient = new CourierClient();
        String firstName = RandomStringUtils.randomAlphabetic(2,18);
        Response postRequestCreateCourier = courierClient.getPostRequestCreateCourier(new Courier("", "", firstName));
        postRequestCreateCourier
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", Matchers.is("Недостаточно данных для создания учетной записи"));
    }

}