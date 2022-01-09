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
import static org.junit.Assert.assertEquals;

public class GetOrdersFromIndividualUserTest {

    private static SpaсeUserClient spaсeUserClient;
    public SpaceUser spaceUser = SpaceUser.getRandom();
    private static LoginUserClient loginUserClient;
    public String accessUserToken;
    public ValidatableResponse createdUser;
    HashMap<String, List> orderHash;
    List<String> ingredients = new ArrayList<>();
    public CreateOrderClient createOrderClient;
    public IngredientsClient ingredientsClient;
    private OrdersUserClient ordersUserClient;


    @Step
    @Before
    public void setUp() {
        spaсeUserClient = new SpaсeUserClient();
        loginUserClient = new LoginUserClient();
        createdUser = spaсeUserClient.create(spaceUser);
        orderHash = new HashMap<>();
        ingredientsClient = new IngredientsClient();
        createOrderClient = new CreateOrderClient();
    }

    @Test
    @DisplayName("Check getting list of 1 order for logined users With Orders")
    public void getOrdersFromLoginedUserWithOrders(){
        //Arrange
        accessUserToken = createdUser.extract().path("accessToken");
        SpaceUser logindData = new SpaceUser(spaceUser.getWrongEmail(), spaceUser.getWrongPassword(), spaceUser.getWrongName());
        loginUserClient.login(new SpaceUserCredentials(logindData.getEmail(), logindData.getPassword()));
        ValidatableResponse listOfIngredients = ingredientsClient.getIngredients();
        List<String> clearIngredients = listOfIngredients.extract().path("data._id");
        ingredients.add(clearIngredients.get(1));
        ingredients.add(clearIngredients.get(5));
        ingredients.add(clearIngredients.get(10));
        orderHash.put("ingredients", ingredients);
        createOrderClient.createOrder(orderHash);
        //Act
        ordersUserClient = new OrdersUserClient();
        ValidatableResponse userOrder = ordersUserClient.getOrders(accessUserToken);
        //Assert
        assertEquals("Статус-код не == 200!",200,userOrder.extract().statusCode());
        assertEquals("Количество заказов не совпадает", "1", userOrder.extract().path("total"));
        assertEquals("Количество заказов не совпадает", "1", userOrder.extract().path("totalToday"));

    }
    @Test
    @DisplayName("Check getting list of 50 orders for logined users With Orders")
    public void getOrdersFromLoginedUserWith50Orders(){
        //Arrange
        accessUserToken = createdUser.extract().path("accessToken");
        SpaceUser logindData = new SpaceUser(spaceUser.getWrongEmail(), spaceUser.getWrongPassword(), spaceUser.getWrongName());
        loginUserClient.login(new SpaceUserCredentials(logindData.getEmail(), logindData.getPassword()));
        ValidatableResponse listOfIngredients = ingredientsClient.getIngredients();
        List<String> clearIngredients = listOfIngredients.extract().path("data._id");
        ingredients.add(clearIngredients.get(1));
        ingredients.add(clearIngredients.get(5));
        ingredients.add(clearIngredients.get(10));
        orderHash.put("ingredients", ingredients);
        for  (int i = 1; i <= 52; i = i + 1) {
            createOrderClient.createOrder(orderHash);
        }
        //Act
        ordersUserClient = new OrdersUserClient();
        ValidatableResponse userOrder = ordersUserClient.getOrders(accessUserToken);
        //Assert
        assertEquals("Статус-код не == 200!",200,userOrder.extract().statusCode());
        assertEquals("Количество заказов не совпадает", "50", userOrder.extract().path("total"));
        assertEquals("Количество заказов не совпадает", "50", userOrder.extract().path("totalToday"));

    }
    @Test
    @DisplayName("Check getting list of 30 orders for logined users With Orders")
    public void getOrdersFromLoginedUserWith30Orders(){
        //Arrange
        accessUserToken = createdUser.extract().path("accessToken");
        SpaceUser logindData = new SpaceUser(spaceUser.getWrongEmail(), spaceUser.getWrongPassword(), spaceUser.getWrongName());
        loginUserClient.login(new SpaceUserCredentials(logindData.getEmail(), logindData.getPassword()));
        ValidatableResponse listOfIngredients = ingredientsClient.getIngredients();
        List<String> clearIngredients = listOfIngredients.extract().path("data._id");
        ingredients.add(clearIngredients.get(1));
        ingredients.add(clearIngredients.get(5));
        ingredients.add(clearIngredients.get(10));
        orderHash.put("ingredients", ingredients);
        for  (int i = 1; i <= 30; i = i + 1) {
            createOrderClient.createOrder(orderHash);
        }
        //Act
        ordersUserClient = new OrdersUserClient();
        ValidatableResponse userOrder = ordersUserClient.getOrders(accessUserToken);
        //Assert
        assertEquals("Статус-код не == 200!",200,userOrder.extract().statusCode());
        assertEquals("Количество заказов не совпадает", "30", userOrder.extract().path("total"));
        assertEquals("Количество заказов не совпадает", "30", userOrder.extract().path("totalToday"));

    }

    @Test
    @DisplayName("Check getting empty list of orders for logined users Without Orders")
    public void getOrdersFromLoginedUserWithoutOrders(){
        //Arrange
        accessUserToken = createdUser.extract().path("accessToken");
        SpaceUser logindData = new SpaceUser(spaceUser.getWrongEmail(), spaceUser.getWrongPassword(), spaceUser.getWrongName());
        loginUserClient.login(new SpaceUserCredentials(logindData.getEmail(), logindData.getPassword()));
        //Act
        ordersUserClient = new OrdersUserClient();
        ValidatableResponse userOrder = ordersUserClient.getOrders(accessUserToken);
        //Assert
        assertEquals("Статус-код не == 200!",200,userOrder.extract().statusCode());
        assertEquals("Количество заказов не совпадает", "0", userOrder.extract().path("total"));
        assertEquals("Количество заказов не совпадает", "0", userOrder.extract().path("totalToday"));

    }

    @Test
    @DisplayName("Check error getting list of 1 orders for not logined users Without Orders")
    public void getOrdersFromNotLoginedUserWithOrders(){
        //Arrange
        accessUserToken = createdUser.extract().path("accessToken");
        SpaceUser logindData = new SpaceUser(spaceUser.getWrongEmail(), spaceUser.getWrongPassword(), spaceUser.getWrongName());
        loginUserClient.login(new SpaceUserCredentials(logindData.getEmail(), logindData.getPassword()));
        ValidatableResponse listOfIngredients = ingredientsClient.getIngredients();
        List<String> clearIngredients = listOfIngredients.extract().path("data._id");
        ingredients.add(clearIngredients.get(1));
        ingredients.add(clearIngredients.get(5));
        ingredients.add(clearIngredients.get(10));
        orderHash.put("ingredients", ingredients);
        createOrderClient.createOrder(orderHash);
        //Act
        ordersUserClient = new OrdersUserClient();
        ValidatableResponse userOrder = ordersUserClient.getOrdersWithoutToken();
        //Assert
        assertEquals("Статус-код не == 401!",401,userOrder.extract().statusCode());
        assertFalse("Параметр success !=false",userOrder.extract().path("success"));
        assertEquals("Сообщение не совпадает",  "You should be authorised", userOrder.extract().path("message"));

    }

    @Test
    @DisplayName("Check error getting empty list of orders for not logined users Without Orders")
    public void getOrdersFromNotLoginedUserWithouthOrders(){
        //Arrange
        accessUserToken = createdUser.extract().path("accessToken");
        SpaceUser logindData = new SpaceUser(spaceUser.getWrongEmail(), spaceUser.getWrongPassword(), spaceUser.getWrongName());
        loginUserClient.login(new SpaceUserCredentials(logindData.getEmail(), logindData.getPassword()));
        //Act
        ordersUserClient = new OrdersUserClient();
        ValidatableResponse userOrder = ordersUserClient.getOrdersWithoutToken();
        //Assert
        assertEquals("Статус-код не == 401!",401,userOrder.extract().statusCode());
        assertFalse("Параметр success !=false",userOrder.extract().path("success"));
        assertEquals("Сообщение не совпадает",  "You should be authorised", userOrder.extract().path("message"));

    }
}
