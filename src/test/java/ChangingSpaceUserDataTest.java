import client.ChangingDataClient;
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

public class ChangingSpaceUserDataTest {

    public ChangingDataClient changingDataClient;
    private static SpaсeUserClient spaсeUserClient;
    public SpaceUser spaceUser = SpaceUser.getRandom();
    private static LoginUserClient loginUserClient;
    public String accessUserToken;
    public String refreshToken;
    public SpaceUser expectedData;
    public ValidatableResponse createdUser;


    @Step
    @Before
    public void setUp() {
        changingDataClient = new ChangingDataClient();
        spaсeUserClient = new SpaсeUserClient();
        loginUserClient = new LoginUserClient();
        createdUser = spaсeUserClient.create(spaceUser);
        accessUserToken = createdUser.extract().path("accessToken");
    }

    @Step
    @After
    public void tearDown() {
        loginUserClient.delete(accessUserToken);
    }


    @Test
    @DisplayName("Check successful editing all user data")
    public void editingSpaceUserAllInfo(){
        //Arrange
        //Act
        expectedData = new SpaceUser(spaceUser.getWrongEmail(), spaceUser.getWrongPassword(), spaceUser.getWrongName());
        ValidatableResponse editedData = changingDataClient.editInfo(expectedData,accessUserToken);
        ValidatableResponse editedUserLogined = loginUserClient.login(new SpaceUserCredentials(expectedData.getEmail(), expectedData.getPassword()));
        //Assert
        assertEquals("Статус-код не == 200!",200,editedData.extract().statusCode());
        assertEquals("Емейл после редактирования не совпадает с введенными данными",expectedData.email,editedData.extract().path("user.email"));
        assertEquals("Имя после редактирования не совпадает с введенными данными", expectedData.name,editedData.extract().path("user.name"));
        assertEquals("Юзер не залогинился с новыми данными!",200,editedUserLogined.extract().statusCode());
    }

    @Test
    @DisplayName("Check not successful editing all user data without authorization")
    public void editingSpaceUserAllInfoNotLogged(){
        //Arrange
        refreshToken = createdUser.extract().path("refreshToken");
        spaсeUserClient.logout(refreshToken);
        String message = "You should be authorised";
        //Act
        expectedData = new SpaceUser(spaceUser.getWrongEmail(), spaceUser.getWrongPassword(), spaceUser.getWrongName());
        ValidatableResponse editedData = changingDataClient.editInfoWithoutToken(expectedData);
        ValidatableResponse editedUserLogined = loginUserClient.login(new SpaceUserCredentials(expectedData.getEmail(), expectedData.getPassword()));
        //Assert
        assertEquals("Статус-код не == 401!",401,editedData.extract().statusCode());
        assertFalse("Параметр success != false",editedData.extract().path("success"));
        assertEquals("Сообщение не соответствует требованиям!",message,editedData.extract().path("message"));
        assertEquals("Юзер залогинился с несуществующими данными!",401,editedUserLogined.extract().statusCode());
    }

    @Test
    @DisplayName("Check not successful editing all user data with email already exist")
    public void editingSpaceUserInfoWithAlreadyExistEmail(){
        //Arrange
        SpaceUser spaceUser2 = SpaceUser.getRandom();
        ValidatableResponse createdUser2 = spaсeUserClient.create(spaceUser2);
        String message = "User with such email already exists";
        //Act
        expectedData = new SpaceUser(spaceUser2.getEmail(), spaceUser.getPassword(), spaceUser.getName());
        ValidatableResponse editedData = changingDataClient.editInfo(expectedData,accessUserToken);
        ValidatableResponse editedUserLogined = loginUserClient.login(new SpaceUserCredentials(expectedData.getEmail(), spaceUser.getPassword()));
        //Assert
        assertEquals("Статус-код не == 403!",403,editedData.extract().statusCode());
        assertFalse("Параметр success != false",editedData.extract().path("success"));
        assertEquals("Сообщение не соответствует требованиям!",message,editedData.extract().path("message"));
        assertEquals("Юзер залогинился с несуществующими данными!",401,editedUserLogined.extract().statusCode());
    }

}
