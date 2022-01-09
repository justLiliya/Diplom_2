import client.*;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.SpaceUser;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class CreateOrderEmptyDataTest {

    HashMap<String, List> orderHash;
    List<String> ingredients = new ArrayList<>();
    public CreateOrderClient createOrderClient;
    public IngredientsClient ingredientsClient;
    public SpaсeUserClient spaсeUserClient;
    public LoginUserClient loginUserClient;
    public ValidatableResponse createdUser;

    @Step
    @Before
    public void setUp() {
        createOrderClient = new CreateOrderClient();
        ingredientsClient = new IngredientsClient();
        orderHash = new HashMap<>();
        orderHash.put("ingredients", ingredients);
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
        SpaceUser spaceUser = SpaceUser.getRandom();
        spaсeUserClient = new SpaсeUserClient();
        loginUserClient = new LoginUserClient();
        createdUser = spaсeUserClient.create(spaceUser);
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
