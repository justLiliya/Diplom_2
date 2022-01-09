package client;

import com.github.javafaker.Faker;
import model.SpaceUser;

import java.util.Locale;

public class SpaceUserCredentials {

    public String email;
    public String password;

    public SpaceUserCredentials(String email, String password){
        this.email = email;
        this.password = password;
    }

    public SpaceUserCredentials(){

    }

    public static SpaceUserCredentials from (SpaceUser spaceUser){
        return new SpaceUserCredentials(spaceUser.getEmail(), spaceUser.getPassword());
    }

    public SpaceUserCredentials setEmail(String email) {
        this.email = email;
        return this;
    }

    public SpaceUserCredentials setPassword(String password) {
        this.password = password;
        return this;
    }


}
