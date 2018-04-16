package ceptebakicim.com.Pojo;

/**
 * Created by SD on 12.02.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class Mesaj {
    private String gonderen, mesaj, zaman;

    public Mesaj() {
    }

    public Mesaj(String gonderen, String mesaj, String zaman) {
        this.gonderen = gonderen;
        this.mesaj = mesaj;
        this.zaman = zaman;
    }

    public String getGonderen() {
        return gonderen;
    }

    public String getMesaj() {
        return mesaj;
    }

    public String getZaman() {
        return zaman;
    }
}
