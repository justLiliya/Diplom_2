import client.*;
import model.SpaceUser;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateSpaceUserTest {

    private SpaсeUserClient spaсeUserClient;
    private boolean spaсeUserLogined;
    private String spaсeUserToken;
    String expectedMessageWithoutParam = "Email, password and name are required fields";

    @Step
    @Before
    public void setUp() {
        spaсeUserClient = new SpaсeUserClient();
    }

    @Test
    @DisplayName("Check new user successfully creation")
    public void createSpaceUserAndCheckResponseTest(){
        //Arrange
        SpaceUser spaceUser = SpaceUser.getRandom();
        //Act
        ValidatableResponse SpaceUserCreated = spaсeUserClient.create(spaceUser);
        spaсeUserToken = SpaceUserCreated.extract().path("refreshToken");
        spaсeUserClient.logout(spaсeUserToken);
        spaсeUserLogined = spaсeUserClient.login(new SpaceUserCredentials(spaceUser.getEmail(), spaceUser.getPassword())).extract().path("success");
        //Assert
        assertEquals("Статус не 200 ок!",200, SpaceUserCreated.extract().statusCode());
        assertNotNull("accessToken oтсутствует!",SpaceUserCreated.extract().path("accessToken"));
        assertNotNull("refreshToken oтсутствует!",SpaceUserCreated.extract().path("refreshToken"));
        assertEquals("Параметр success != true",spaсeUserLogined, true);


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
        SpaсeUserWithoutPassword spaсeUserWithoutPassword = SpaсeUserWithoutPassword.getRandom();;
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
