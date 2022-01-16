package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Ingredients;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class CreateOrderClient extends RequestClient{

    private static final String USER_PATH = "/api/";


    @Step
    public ValidatableResponse createOrder (HashMap ingredients){

        return
                given()
                        .spec(getBaseSpec())
                        .body( ingredients)
                        .when()
                        .post(USER_PATH + "orders")
                        .then();




    }
}
