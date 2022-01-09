package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.SpaceUser;

import static io.restassured.RestAssured.given;

public class ChangingDataClient extends RequestClient{

    private static final String USER_PATH = "/api/auth/";

    @Step
    public ValidatableResponse getInfo(String accessUserToken){

        return
                given()
                        .header("Authorization", accessUserToken)
                        .spec(getBaseSpec())
                        .when()
                        .get(USER_PATH + "user")
                        .then();




    }

    @Step
    public ValidatableResponse editInfo(Object object, String accessUserToken){

        return
                given()
                        .header("Authorization", accessUserToken)
                        .spec(getBaseSpec())
                        .body(object)
                        .when()
                        .patch(USER_PATH + "user")
                        .then();

    }

    @Step
    public ValidatableResponse editInfoWithoutToken(Object object){

        return
                given()
                        .spec(getBaseSpec())
                        .body(object)
                        .when()
                        .patch(USER_PATH + "user")
                        .then();

    }



}
