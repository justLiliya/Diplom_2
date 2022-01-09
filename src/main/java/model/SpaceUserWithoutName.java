package model;


import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;

//POJO — plain old Java object(Класс с данными для создания космического юзера без емейла)
public class SpaceUserWithoutName {

    static Faker faker = new Faker(new Locale("en_EN"));

    public String password;
    public String email;

    public SpaceUserWithoutName(String email, String password){
        this.email = email;
        this.password = password;

    }

    public SpaceUserWithoutName(){

    }

    //Хелпер-метод, генерирующий данные
    public static SpaceUserWithoutName getRandom() {

        final String email = faker.internet().emailAddress();
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new SpaceUserWithoutName(email, password);

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
