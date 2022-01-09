import client.*;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.SpaceUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CreateOrderInvalidHashTest {
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
        String invalid = RandomStringUtils.randomAlphanumeric(10,20);
        ingredients.add(invalid);
        orderHash.put("ingredients", ingredients);
    }


    @Test
    @DisplayName("Check new order unsuccessfully creation with invalid hash without auth")
    public void CreateOrderWithoutAuthInvalidDataTest(){
        //Act
        ValidatableResponse createdOrder = createOrderClient.createOrder(orderHash);
        //Assert
        assertEquals("Статус не 500!",500, createdOrder.extract().statusCode());

    }

    @Test
    @DisplayName("Check new order unsuccessfully creation with invalid hash with auth")
    public void CreateOrderWithAuthInvalidDataTest(){
        //Arrange
        SpaceUser spaceUser = SpaceUser.getRandom();
        spaсeUserClient = new SpaсeUserClient();
        loginUserClient = new LoginUserClient();
        createdUser = spaсeUserClient.create(spaceUser);
        loginUserClient.login(new SpaceUserCredentials(spaceUser.getEmail(), spaceUser.getPassword()));
        //Act
        ValidatableResponse createdOrder = createOrderClient.createOrder(orderHash);
        //Assert
        assertEquals("Статус не 500!",500, createdOrder.extract().statusCode());

    }

}
