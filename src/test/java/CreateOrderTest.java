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

public class CreateOrderTest {

    HashMap<String, List> orderHash;
    List<String> ingredients = new ArrayList<>();
    public CreateOrderClient createOrderClient;
    public IngredientsClient ingredientsClient;
    public SpaсeUserClient spaсeUserClient;
    public LoginUserClient loginUserClient;
    public ValidatableResponse createdUser;
    private String accessToken;

    @Step
    @Before
    public void setUp() {
        createOrderClient = new CreateOrderClient();
        ingredientsClient = new IngredientsClient();
        List<String> ingredients = new ArrayList<>();
        ValidatableResponse listOfIngredients = ingredientsClient.getIngredients();
        List<String> clearIngredients = listOfIngredients.extract().path("data._id");
        for (int i = 1; i <= 5; i = i + 1) {
            ingredients.add(clearIngredients.get(i+5));
        }
        orderHash = new HashMap<>();
        orderHash.put("ingredients", ingredients);
    }

    @Step
    @After
    public void tearDown() {
        loginUserClient.delete(accessToken);
    }


    @Test
    @DisplayName("Check new order successfully creation With Authorization")
    public void createOrderWithAuthTest(){
        //Arrange
        SpaceUser spaceUser = SpaceUser.getRandom();
        spaсeUserClient = new SpaсeUserClient();
        loginUserClient = new LoginUserClient();
        createdUser = spaсeUserClient.create(spaceUser);
        ValidatableResponse logined = loginUserClient.login(new SpaceUserCredentials(spaceUser.getEmail(), spaceUser.getPassword()));
        accessToken = logined.extract().path("accessToken");
        //Act
        ValidatableResponse createdOrder = createOrderClient.createOrder(orderHash);
        //Assert
        assertEquals("Статус не 200 ок!",200, createdOrder.extract().statusCode());
        assertTrue("Параметр success не соответствует требованиям!",createdOrder.extract().path("success"));
        assertNotNull("Название бургера отсутствует!",createdOrder.extract().path("name"));
        assertNotNull("Номер заказа oтсутствует!",createdOrder.extract().path("order.number"));

    }

    @Test
    @DisplayName("Check new order not successfully creation Without Authorization")
    public void createOrderWithoutAuthTest(){
        //Act
        ValidatableResponse createdOrder = createOrderClient.createOrder(orderHash);
        accessToken = createdOrder.extract().path("accessToken");
        //Assert
        assertEquals("Статус не 400 ок!",401, createdOrder.extract().statusCode());
        assertFalse("Параметр success не соответствует требованиям!",createdOrder.extract().path("success"));
        assertEquals("Сообщение не соответствует требованиям!","You should be authorised",createdOrder.extract().path("message"));

    }

}
