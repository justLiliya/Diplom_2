package client;

import client.RequestClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class IngredientsClient extends RequestClient {

    private static final String USER_PATH = "/api/";

    @Step
    public ValidatableResponse getIngredients (){

        return
                given()
                        .spec(getBaseSpec())
                        .when()
                        .get(USER_PATH + "ingredients")
                        .then();




    }


}
