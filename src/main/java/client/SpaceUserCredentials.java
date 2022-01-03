package client;

import model.SpaceUser;

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
        return new SpaceUserCredentials(spaceUser.email, spaceUser.password);
    }

    public SpaceUserCredentials setEmail(String email) {
        this.email = email;
        return this;
    }

    public SpaceUserCredentials setPassword(String password) {
        this.password = password;
        return this;
    }

    public static SpaceUserCredentials getWithEmailOnly(SpaceUser spaceUser) {
        return new SpaceUserCredentials().setEmail(spaceUser.email);
    }

    public static SpaceUserCredentials getWithPasswordOnly(SpaceUser spaceUser) {
        return new SpaceUserCredentials().setPassword(spaceUser.password);
    }

}
