import client.SpaceUserCredentials;
import client.SpaсeUserClient;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.SpaceUser;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateSpaceUserTest {

    private SpaсeUserClient spaсeUserClient;
    private boolean spaсeUserLogined;
    private String spaсeUserToken;

    @Step
    @Before
    public void setUp() {

        spaсeUserClient = new SpaсeUserClient();
    }

    @Test
    @DisplayName("Check new user creation")
    public void createSpaceUserAndCheckResponseTest(){
        //Arrange
        SpaceUser spaceUser = SpaceUser.getRandom();
        //Act
        ValidatableResponse SpaceUserCreated = spaсeUserClient.create(spaceUser);
        spaсeUserToken = SpaceUserCreated.extract().path("refreshToken");
        spaсeUserClient.logout(spaсeUserToken);
        spaсeUserLogined = spaсeUserClient.login(new SpaceUserCredentials(spaceUser.email, spaceUser.password)).extract().path("success");
        /**C использование паттерна фабрика - берет креденшиалы из нашего курьера
         courierId = courierClient.login(Couriercredentials.from(courier));**/
        //Assert
        assertEquals("Статус не 200 ок.Юзер не создан!",200, SpaceUserCreated.extract().statusCode());
        assertNotNull("accessToken oтсутствует!",SpaceUserCreated.extract().path("accessToken"));
        assertNotNull("refreshToken oтсутствует!",SpaceUserCreated.extract().path("refreshToken"));
        assertEquals("success не тру",spaсeUserLogined, true);


    }



}
