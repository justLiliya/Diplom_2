package client;


import com.github.javafaker.Faker;

import java.util.Locale;

//POJO — plain old Java object(Класс с данными для создания космического юзера без пароля)
public class SpaсeUserWithoutPassword {

    static Faker faker = new Faker(new Locale("en_EN"));

    public String email;
    public String name;

    public SpaсeUserWithoutPassword(String email, String name){
        this.email = email;
        this.name = name;

    }

    public SpaсeUserWithoutPassword(){

    }

    //Хелпер-метод, генерирующий данные
    public static SpaсeUserWithoutPassword getRandom() {
        final String email = faker.internet().emailAddress();
        final String name = faker.name().firstName();
        return new SpaсeUserWithoutPassword(email, name);

    }

}
