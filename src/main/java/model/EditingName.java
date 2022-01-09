package model;

import com.github.javafaker.Faker;

import java.util.Locale;

public class EditingName {

    static Faker faker = new Faker(new Locale("en_EN"));

    public String name;

    public EditingName(String name){
        this.name = name;

    }

    //Хелпер-метод, генерирующий данные
    public static EditingName getRandom() {
        final String name = faker.name().firstName();
        return new EditingName(name);
    }

    public String getOnlyName() {
        return name;
    }
}
