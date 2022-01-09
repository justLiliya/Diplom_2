package model;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;

public class EditingPassword {

    static Faker faker = new Faker(new Locale("en_EN"));

    public String password;

    public EditingPassword(String password){
        this.password = password;

    }

    //Хелпер-метод, генерирующий данные
    public static EditingPassword getRandom() {
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new EditingPassword(password);
    }

    public String getOnlyPassword() {
        return password;
    }
}
