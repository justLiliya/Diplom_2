import client.LoginUserClient;
import client.SpaceUserCredentials;
import client.SpaсeUserClient;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.SpaceUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class LoginSpaceUserTest {

    public LoginUserClient loginUserClient;
    SpaceUser spaceUser = SpaceUser.getRandom();
    private boolean spaсeUserLogined;
    private String message = "email or password are incorrect";
    private String spaсeUserToken;
    private String accessToken;



    @Step
    @Before
    public void setUp() throws InterruptedException {
        loginUserClient = new LoginUserClient();
        ValidatableResponse user = loginUserClient.create(spaceUser);
        spaсeUserToken = user.extract().path("refreshToken");
        accessToken = user.extract().path("accessToken");
        loginUserClient.logout(spaсeUserToken);
        Thread.sleep(1000);
    }

    @Step
     @After
     public void tearDown() {
        loginUserClient.delete(accessToken);
     }

    @Test
    @DisplayName("Check user logined successfully")
    public void loginSpaceUserAndCheckResponseTest(){
        //Act
        ValidatableResponse spaсeUserLogined = loginUserClient.login(new SpaceUserCredentials(spaceUser.getEmail(), spaceUser.getPassword()));
        //Assert
        assertEquals("Статус не 200 ок!",200, spaсeUserLogined.extract().statusCode());
        assertNotNull("accessToken oтсутствует!",spaсeUserLogined.extract().path("accessToken"));
        assertNotNull("refreshToken oтсутствует!",spaсeUserLogined.extract().path("refreshToken"));
        assertTrue("Параметр success при логине != true",spaсeUserLogined.extract().path("success"));


    }

    @Test
    @DisplayName("Check user logined unsuccessfully with wrong email")
    public void unloginSpaceUserWithWrongEmailTest(){
        //Act
        ValidatableResponse spaсeUserLogined = loginUserClient.login(new SpaceUserCredentials(spaceUser.getWrongEmail(), spaceUser.getPassword()));
        //Assert
        assertEquals("Статус не 401!",401, spaсeUserLogined.extract().statusCode());
        assertEquals("Текст сообщения не соответствует требованиям!", message,spaсeUserLogined.extract().path("message"));
        assertFalse("Параметр success при логине != false",spaсeUserLogined.extract().path("success"));


    }

    @Test
    @DisplayName("Check user logined unsuccessfully with wrong password")
    public void unloginSpaceUserWithWrongPasswordTest(){
        //Act
        ValidatableResponse spaсeUserLogined = loginUserClient.login(new SpaceUserCredentials(spaceUser.getEmail(), spaceUser.getWrongPassword()));
        //Assert
        assertEquals("Статус не 401!",401, spaсeUserLogined.extract().statusCode());
        assertEquals("Текст сообщения не соответствует требованиям!", message,spaсeUserLogined.extract().path("message"));
        assertFalse("Параметр success при логине != false",spaсeUserLogined.extract().path("success"));
    }

    @Test
    @DisplayName("Check user logined unsuccessfully with wrong data")
    public void unloginSpaceUserWithWrongDataTest(){
        //Act
        ValidatableResponse spaсeUserLogined = loginUserClient.login(new SpaceUserCredentials(spaceUser.getWrongEmail(), spaceUser.getWrongPassword()));
        //Assert
        assertEquals("Статус не 401!",401, spaсeUserLogined.extract().statusCode());
        assertEquals("Текст сообщения не соответствует требованиям!", message,spaсeUserLogined.extract().path("message"));
        assertFalse("Параметр success при логине != false",spaсeUserLogined.extract().path("success"));
    }

}
