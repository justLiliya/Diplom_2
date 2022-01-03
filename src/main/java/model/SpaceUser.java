package model;

import java.util.Locale;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

//POJO — plain old Java object(Класс с данными для создания космического юзера)
public class SpaceUser {
    static Faker faker = new Faker(new Locale("en_EN"));

    public final String email;
    public final String password;
    public final String name;

    public SpaceUser(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;

    }

    /**public SpaceUser(){ пока закомменчу - идея ругается

    }**/

    //Хелпер-метод, генерирующий данные
    public static SpaceUser getRandom() {

        final String email = faker.internet().emailAddress();
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = faker.name().firstName();
        return new SpaceUser(email, password, name);

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return password;
    }

    public String setEmail(String email) {
        return email;
    }

    public String setPassword(String password) {
        return password;
    }

    public String setName(String name) {
        return name;
    }





}
