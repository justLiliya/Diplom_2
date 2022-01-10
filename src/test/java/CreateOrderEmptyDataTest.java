import client.*;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.SpaceUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class CreateOrderEmptyDataTest {

    HashMap<String, List> orderHash;
    List<String> ingredients = new ArrayList<>();
    SpaceUser spaceUser = SpaceUser.getRandom();
    public CreateOrderClient createOrderClient;
    public IngredientsClient ingredientsClient;
    public SpaсeUserClient spaсeUserClient;
    public LoginUserClient loginUserClient;
    public ValidatableResponse createdUser;
    private String accessToken;

    @Step
    @Before
    public void setUp() {
        spaсeUserClient = new SpaсeUserClient();
        loginUserClient = new LoginUserClient();
        createdUser = spaсeUserClient.create(spaceUser);
        accessToken = createdUser.extract().path("accessToken");
        createOrderClient = new CreateOrderClient();
        ingredientsClient = new IngredientsClient();
        orderHash = new HashMap<>();
        orderHash.put("ingredients", ingredients);
    }

    @Step
    @After
    public void tearDown() {
        loginUserClient.delete(accessToken);
    }


    @Test
    @DisplayName("Check new order unsuccessfully creation with empty data without auth")
    public void CreateOrderWithoutAuthTestEmptyData(){
        //Act
        ValidatableResponse createdOrder = createOrderClient.createOrder(orderHash);
        String message = "Ingredient ids must be provided";
        //Assert
        assertEquals("Статус не 400!",400, createdOrder.extract().statusCode());
        assertFalse("Параметр success !=false!",createdOrder.extract().path("success"));
        assertEquals("Текст сообщения не соответствует требованиям!",message, createdOrder.extract().path("message"));

    }

    @Test
    @DisplayName("Check new order unsuccessfully creation with empty data with auth")
    public void CreateOrderWithAuthTestEmptyData(){
        //Arrange
        loginUserClient.login(new SpaceUserCredentials(spaceUser.getEmail(), spaceUser.getPassword()));
        //Act
        ValidatableResponse createdOrder = createOrderClient.createOrder(orderHash);
        String message = "Ingredient ids must be provided";
        //Assert
        assertEquals("Статус не 400!",400, createdOrder.extract().statusCode());
        assertFalse("Параметр success !=false!",createdOrder.extract().path("success"));
        assertEquals("Текст сообщения не соответствует требованиям!",message, createdOrder.extract().path("message"));

    }
}
