import client.ChangingDataClient;
import client.LoginUserClient;
import client.SpaceUserCredentials;
import client.SpaсeUserClient;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChangingSpaceUserDataWithoutParamsTest {

    public ChangingDataClient changingDataClient;
    public SpaceUser spaceUser = SpaceUser.getRandom();
    private static SpaсeUserClient spaсeUserClient;
    private static LoginUserClient loginUserClient;
    public String accessUserToken;
    public SpaceUserWithoutName expectedData;
    public EditingEmail expectedEmailData;
    public EditingName expectedNameData;
    public EditingPassword expectedPasswordData;
    public ValidatableResponse createdUser;

    @Step
    @Before
    public void setUp() throws InterruptedException {
        changingDataClient = new ChangingDataClient();
        spaсeUserClient = new SpaсeUserClient();
        loginUserClient = new LoginUserClient();
        createdUser = spaсeUserClient.create(spaceUser);
        accessUserToken = createdUser.extract().path("accessToken");
        Thread.sleep(1000);
    }

    @Step
    @After
    public void tearDown() {
        loginUserClient.delete(accessUserToken);
    }

    @Test
    @DisplayName("Check successful editing email and password of user")
    public void editingSpaceUserEmailAndPassword(){
        //Arrange
        String expectedName = createdUser.extract().path("user.name");
        SpaceUserWithoutName spaceUserWithoutName = SpaceUserWithoutName.getRandom();
        //Act
        expectedData = new SpaceUserWithoutName(spaceUserWithoutName.getEmail(), spaceUserWithoutName.getPassword());
        ValidatableResponse editedData = changingDataClient.editInfo(expectedData,accessUserToken);
        ValidatableResponse editedUserLogined = loginUserClient.login(new SpaceUserCredentials(expectedData.getEmail(), expectedData.getPassword()));
        //Assert
        assertEquals("Статус-код не == 200!",200,editedData.extract().statusCode());
        assertEquals("Емейл после редактирования не совпадает с введенными данными",expectedData.email,editedData.extract().path("user.email"));
        assertEquals("Имя не должно отличаться, оно не было отредактировано!", expectedName,editedData.extract().path("user.name"));
        assertEquals("Юзер не залогинился с новыми данными!",200,editedUserLogined.extract().statusCode());

    }

    @Test
    @DisplayName("Check successful editing email ")
    public void editingSpaceUserEmail(){
        //Arrange
        EditingEmail editingData = EditingEmail.getRandom();
        //Act
        expectedEmailData = new EditingEmail(editingData.getOnlyEmail());
        ValidatableResponse editedData = changingDataClient.editInfo(expectedEmailData,accessUserToken);
        ValidatableResponse editedUserLogined = loginUserClient.login(new SpaceUserCredentials(expectedEmailData.getOnlyEmail(), spaceUser.getPassword()));
        //Assert
        assertEquals("Статус-код не == 200!",200,editedData.extract().statusCode());
        assertEquals("Емейл после редактирования не совпадает с введенными данными",expectedEmailData.email,editedData.extract().path("user.email"));
        assertEquals("Имя изменилось!", spaceUser.name,editedData.extract().path("user.name"));
        assertEquals("Юзер не залогинился с новыми данными!",200,editedUserLogined.extract().statusCode());
    }

    @Test
    @DisplayName("Check successful editing name")
    public void editingSpaceUserName(){
        //Arrange
        EditingName editingData = EditingName.getRandom();
        //Act
        expectedNameData = new EditingName(editingData.getOnlyName());
        ValidatableResponse editedData = changingDataClient.editInfo(expectedNameData,accessUserToken);
        ValidatableResponse editedUserLogined = loginUserClient.login(new SpaceUserCredentials(spaceUser.getEmail(), spaceUser.getPassword()));
        //Assert
        assertEquals("Статус-код не == 200!",200,editedData.extract().statusCode());
        assertEquals("Емейл не совпадает!",spaceUser.email,editedData.extract().path("user.email"));
        assertEquals("Имя после редактирования не совпадает с введенными данными", expectedNameData.name,editedData.extract().path("user.name"));
        assertEquals("Юзер не залогинился с новыми данными!",200,editedUserLogined.extract().statusCode());
    }

    @Test
    @DisplayName("Check successful editing password of user")
    public void editingSpaceUserPassword(){
        //Arrange
        EditingPassword editingData = EditingPassword.getRandom();
        //Act
        expectedPasswordData = new EditingPassword(editingData.getOnlyPassword());
        ValidatableResponse editedData = changingDataClient.editInfo(expectedPasswordData,accessUserToken);
        ValidatableResponse editedUserLogined = loginUserClient.login(new SpaceUserCredentials(spaceUser.getEmail(), expectedPasswordData.getOnlyPassword()));
        //Assert
        assertEquals("Статус-код не == 200!",200,editedData.extract().statusCode());
        assertEquals("Емейл не совпадает!",spaceUser.email,editedData.extract().path("user.email"));
        assertEquals("Имя не совпадает!", spaceUser.name,editedData.extract().path("user.name"));
        assertEquals("Юзер не залогинился с новыми данными!",200,editedUserLogined.extract().statusCode());
    }

}
