package ceptebakicim.com.Pojo;

/**
 * Created by SD on 14.01.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class AllKeepersPojo {
    private int id;
    private String name, nationality,thumbnail, calisma, dil, maas,age;

    public AllKeepersPojo(int id, String name, String nationality, String thumbnail, String calisma, String dil, String maas,String age) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.thumbnail = thumbnail;
        this.calisma = calisma;
        this.dil = dil;
        this.maas = maas;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getCalisma() {
        return calisma;
    }

    public String getDil() {
        return dil;
    }

    public String getMaas() {
        return maas;
    }

    public String getAge() {
        return age;
    }

}
