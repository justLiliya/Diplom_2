package model;

import java.util.ArrayList;
import java.util.List;

//POJO — plain old Java object(Класс с данными для создания заказа)
public class Ingredients {


    private final String id;
    public List<String> ingredients = new ArrayList<>();

    public Ingredients(String id,List<String> ingredients) {
        this.id = id;
        this.ingredients = ingredients;
    }


}
