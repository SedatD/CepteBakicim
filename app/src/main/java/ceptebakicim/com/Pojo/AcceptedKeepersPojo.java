package ceptebakicim.com.Pojo;

/**
 * Created by SD on 27.01.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class AcceptedKeepersPojo {
    private int id;
    private String name, durum;

    public AcceptedKeepersPojo(int id, String name, String durum) {
        this.id = id;
        this.name = name;
        this.durum = durum;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDurum() {
        return durum;
    }

}