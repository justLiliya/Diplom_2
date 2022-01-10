package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class LoginUserClient extends RequestClient{

    private static final String USER_PATH = "/api/auth/";

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

        return
                given()
                .spec(getBaseSpec())
                .body(refreshToken)
                .when()
                .post(USER_PATH + "logout")
                .then();

    }

    @Step
    public ValidatableResponse create(Object object){

        return
                given()
                .spec(getBaseSpec())
                .body(object)
                .when()
                .post(USER_PATH + "register")
                .then();

    }

    public void delete(String accessToken) {
        if (accessToken == null) {
            return;
        }
                given()
                .header("Authorization",accessToken)
                .spec(getBaseSpec())
                .when()
                .delete(USER_PATH + "user")
                .then()
                .statusCode(202);
    }
}
