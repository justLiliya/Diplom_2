package client;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;
//POJO — plain old Java object(Класс с данными для создания космического юзера без емейла)
public class SpaсeUserWithoutEmail {

    static Faker faker = new Faker(new Locale("en_EN"));

    public String password;
    public String name;

    public SpaсeUserWithoutEmail(String password, String name){
        this.password = password;
        this.name = name;

    }

    public SpaсeUserWithoutEmail(){

    }

    //Хелпер-метод, генерирующий данные
    public static SpaсeUserWithoutEmail getRandom() {
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = faker.name().firstName();
        return new SpaсeUserWithoutEmail(password, name);

    }


}
