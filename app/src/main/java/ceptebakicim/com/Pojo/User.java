package ceptebakicim.com.Pojo;

/**
 * Created by SD on 13.02.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class User {
    private String name,mail,oneSignalId;

    public User() {
    }

    public User(String name, String mail, String oneSignalId) {
        this.name = name;
        this.mail = mail;
        this.oneSignalId = oneSignalId;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getOneSignalId() {
        return oneSignalId;
    }

}
