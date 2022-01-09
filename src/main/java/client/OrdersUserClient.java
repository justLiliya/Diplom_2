package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrdersUserClient extends RequestClient{

    private static final String USER_PATH = "/api/";


    @Step
    public ValidatableResponse getOrders(String accessUserToken){

        return
                given()
                        .header("Authorization", accessUserToken)
                        .spec(getBaseSpec())
                        .when()
                        .get(USER_PATH + "orders")
                        .then();




    }

    @Step
    public ValidatableResponse getOrdersWithoutToken(){

        return
                given()
                        .spec(getBaseSpec())
                        .when()
                        .get(USER_PATH + "orders")
                        .then();




    }
}
