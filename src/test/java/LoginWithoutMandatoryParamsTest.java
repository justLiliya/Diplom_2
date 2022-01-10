import client.LoginUserClient;
import client.SpaceUserCredentials;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.SpaceUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class LoginWithoutMandatoryParamsTest {

    private final String name;
    private final String password;
    public LoginUserClient loginUserClient;
    public String message = "email or password are incorrect";


    public LoginWithoutMandatoryParamsTest(String name, String password){
        this.name = name;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Object[] getData() {
        SpaceUser spaceUser = SpaceUser.getRandom();
        return new Object[][]{
                {null , spaceUser.getEmail()},
                {spaceUser.getPassword(), null},
                {null, null},
                {"", ""}
        };
    }

    @Step
    @Before
    public void setUp() throws InterruptedException {
        loginUserClient = new LoginUserClient();
        Thread.sleep(1000);
    }


    @Test
    @DisplayName("Check login with different wrong datasets")
    public void LoginWithoutMandatoryParamsTest(){
        //Act
        ValidatableResponse spaсeUserLogined = loginUserClient.login(new SpaceUserCredentials(name, password));
        //Assert
        assertEquals("Статус не 401!",401, spaсeUserLogined.extract().statusCode());
        assertEquals("Текст сообщения не соответствует требованиям!", message,spaсeUserLogined.extract().path("message"));
        assertFalse("Параметр success при логине != false",spaсeUserLogined.extract().path("success"));

    }


}
