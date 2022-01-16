import client.*;
import model.SpaceUser;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.SpaceUserWithoutName;
import model.SpaсeUserWithoutEmail;
import model.SpaсeUserWithoutPassword;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateSpaceUserTest {

    private SpaсeUserClient spaсeUserClient;
    private static LoginUserClient loginUserClient;
    private boolean isSuccess;
    private String spaсeUserToken;
    String expectedMessageWithoutParam = "Email, password and name are required fields";
    private String accessToken;



    @Step
    @Before
    public void setUp() {
        spaсeUserClient = new SpaсeUserClient();
        loginUserClient = new LoginUserClient();

    }

    @Step
    @After
    public void tearDown() {
        loginUserClient.delete(accessToken);
    }

    @Test
    @DisplayName("Check new user successfully creation")
    public void createSpaceUserAndCheckResponseTest(){
        //Arrange
        SpaceUser spaceUser = SpaceUser.getRandom();
        //Act
        ValidatableResponse spaceUserCreated = spaсeUserClient.create(spaceUser);
        spaсeUserToken = spaceUserCreated.extract().path("refreshToken");
        spaсeUserClient.logout(spaсeUserToken);
        ValidatableResponse spaсeUserLogined = loginUserClient.login(new SpaceUserCredentials(spaceUser.getEmail(), spaceUser.getPassword()));
        isSuccess = spaсeUserLogined.extract().path("success");
        accessToken = spaсeUserLogined.extract().path("accessToken");
        //Assert
        assertEquals("Статус не 200 ок!",200, spaceUserCreated.extract().statusCode());
        assertNotNull("accessToken oтсутствует!",spaceUserCreated.extract().path("accessToken"));
        assertNotNull("refreshToken oтсутствует!",spaceUserCreated.extract().path("refreshToken"));
        assertTrue("Параметр success при логине != true",isSuccess);


    }

    @Test
    @DisplayName("Check user without email not created")
    public void createSpaceUserWithoutEmailTest(){
        //Arrange
        SpaсeUserWithoutEmail spaсeUserClientWithoutEmail = SpaсeUserWithoutEmail.getRandom();
        //Act
        ValidatableResponse SpaceUserWithoutEmailCreated = spaсeUserClient.create(spaсeUserClientWithoutEmail);
        //Assert
        assertEquals("Статус не 403!",403, SpaceUserWithoutEmailCreated.extract().statusCode());
        assertFalse("Параметр success != false",SpaceUserWithoutEmailCreated.extract().path("success"));
        assertEquals("Текст сообщения не соответствует требованиям!",expectedMessageWithoutParam,SpaceUserWithoutEmailCreated.extract().path("message"));


    }

    @Test
    @DisplayName("Check user without password not created")
    public void createSpaceUserWithoutPasswordTest(){
        //Arrange
        SpaсeUserWithoutPassword spaсeUserWithoutPassword = SpaсeUserWithoutPassword.getRandom();
        //Act
        ValidatableResponse SpaceUserWithoutPasswordCreated = spaсeUserClient.create(spaсeUserWithoutPassword);
        //Assert
        assertEquals("Статус не 403!",403, SpaceUserWithoutPasswordCreated.extract().statusCode());
        assertFalse("Параметр success != false",SpaceUserWithoutPasswordCreated.extract().path("success"));
        assertEquals("Текст сообщения не соответствует требованиям!",expectedMessageWithoutParam,SpaceUserWithoutPasswordCreated.extract().path("message"));


    }

    @Test
    @DisplayName("Check user without name not created")
    public void createSpaceUserWithoutNameTest(){
        //Arrange
        SpaceUserWithoutName spaceUserWithoutName = SpaceUserWithoutName.getRandom();
        //Act
        ValidatableResponse SpaceUserWithoutNameCreated = spaсeUserClient.create(spaceUserWithoutName);
        //Assert
        assertEquals("Статус не 403!",403, SpaceUserWithoutNameCreated.extract().statusCode());
        assertFalse("Параметр success != false",SpaceUserWithoutNameCreated.extract().path("success"));
        assertEquals("Текст сообщения не соответствует требованиям!",expectedMessageWithoutParam,SpaceUserWithoutNameCreated.extract().path("message"));


    }

    @Test
    @DisplayName("Check user already exist not created")
    public void createSpaceUserAlreadyExistTest(){
        //Arrange
        SpaceUser spaceUser = SpaceUser.getRandom();
        spaсeUserClient.create(spaceUser);
        String expectedMessage = "User already exists";
        //Act
        ValidatableResponse SpaceUserAlreadyExistCreated = spaсeUserClient.create(spaceUser);
        //Assert
        assertEquals("Статус не 403!",403, SpaceUserAlreadyExistCreated.extract().statusCode());
        assertFalse("Параметр success != false",SpaceUserAlreadyExistCreated.extract().path("success"));
        assertEquals("Текст сообщения не соответствует требованиям!",expectedMessage,SpaceUserAlreadyExistCreated.extract().path("message"));


    }



}
