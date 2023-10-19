package ru.services.praktikum.scooter.qa;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final String CREATE_COURIER = "api/v1/courier";
    private static final String CREATE_COURIER_LOGIN = "/api/v1/courier/login";

    @Step("Создание курьера, проверка кода ответа")
    public Response getPostRequestCreateCourier(Courier courier) {
        return given().log().all()
                .filter(new AllureRestAssured())
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(CREATE_COURIER);
    }

    @Step("Авторизация курьера в системе, получение id, проверка кода ответа")
    public Response getPostRequestCourierLogin(Courier courier) {
        return given().log().all()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(CREATE_COURIER_LOGIN);
    }
}