package client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class SpaсeUserClient extends RequestClient{

    private static final String USER_PATH = "/api/auth/";

    @Step
    public ValidatableResponse create(Object object){

        return given()
                .spec(getBaseSpec())
                .body(object)
                .when()
                .post(USER_PATH + "register")
                .then();

    }

    @Step
    public ValidatableResponse login(SpaceUserCredentials spaceUserCredentials){

        return
                given()
                .spec(getBaseSpec())
                .body(spaceUserCredentials)
                .when()
                .post(USER_PATH + "login")
                .then();

    }

    @Step
    public ValidatableResponse logout(String refreshToken){

        return given()
                .spec(getBaseSpec())
                .body(refreshToken)
                .when()
                .post(USER_PATH + "logout")
                .then();

    }



}
