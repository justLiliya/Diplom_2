package model;

import com.github.javafaker.Faker;

import java.util.Locale;

public class EditingEmail {

    static Faker faker = new Faker(new Locale("en_EN"));

    public String email;

    public EditingEmail(String email){
        this.email = email;

    }

    //Хелпер-метод, генерирующий данные
    public static EditingEmail getRandom() {
        final String email = faker.internet().emailAddress();
        return new EditingEmail(email);
    }

    public String getOnlyEmail() {
        return email;
    }
}
