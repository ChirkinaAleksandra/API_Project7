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


public class CourierLoginTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Курьер авторизирован")
    @Description("Проверка авторизации курьера с корректным логином и паролем")
    public void checkCreatingCourierLoginTest() {
        CourierClient courierClient = new CourierClient();
        Response postRequestCourierLogin = courierClient.getPostRequestCourierLogin(new Courier("Izumyshka", "12345", "izum"));
        postRequestCourierLogin
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("id", Matchers.notNullValue());
    }

    @Test
    @DisplayName("Курьер авторизирован без логина")
    @Description("Проверка авторизации курьера без ввода логина")
    public void checkVerificationWithoutLoginAuthorization() {
        CourierClient courierClient = new CourierClient();
        String password = RandomStringUtils.randomAlphanumeric(7,15);
        String firstName = RandomStringUtils.randomAlphabetic(2,18);
        Response postRequestCourierLogin = courierClient.getPostRequestCourierLogin(new Courier("", password, firstName));
        postRequestCourierLogin
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", Matchers.is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Курьер авторизирован без пароля")
    @Description("Проверка авторизации курьера без ввода пароля")
    public void checkVerificationWithoutPasswordAuthorization() {
        CourierClient courierClient = new CourierClient();
        String login = RandomStringUtils.randomAlphanumeric(2,15);
        String firstName = RandomStringUtils.randomAlphabetic(2,18);
        Response postRequestCourierLogin = courierClient.getPostRequestCourierLogin(new Courier(login, "", firstName));
        postRequestCourierLogin
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", Matchers.is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Курьер авторизирован под несуществующим логином")
    @Description("Проверка авторизации курьера в системе под несуществующим пользователем")
    public void checkAuthorizationUnderIncorrectLogin() {
        CourierClient courierClient = new CourierClient();
        String login = RandomStringUtils.randomAlphanumeric(2,15);
        String password = RandomStringUtils.randomAlphanumeric(7,15);
        Response postRequestCourierLogin = courierClient.getPostRequestCourierLogin(new Courier(login, password));
        postRequestCourierLogin
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body("message", Matchers.is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Курьер авторизирован под некорректным логином")
    @Description("Проверка авторизации курьера в системе, если неправильно указать логин")
    public void checkEnteringInvalidLogin() {
        CourierClient courierClient = new CourierClient();
        Response postRequestCourierLogin = courierClient.getPostRequestCourierLogin(new Courier("ИзюмШпиц", "212506"));
        postRequestCourierLogin
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body("message", Matchers.is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Курьер авторизирован под некорректным паролем")
    @Description("Проверка авторизации курьера в системе, если неправильно указать пароль")
    public void checkEnteringInvalidPassword() {
        CourierClient courierClient = new CourierClient();
        String password = RandomStringUtils.randomAlphanumeric(7,15);
        Response postRequestCourierLogin = courierClient.getPostRequestCourierLogin(new Courier("izym", password));
        postRequestCourierLogin
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body("message", Matchers.is("Учетная запись не найдена"));
    }
}