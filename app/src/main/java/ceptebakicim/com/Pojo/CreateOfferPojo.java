package ceptebakicim.com.Pojo;

/**
 * Created by SD on 28.01.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class CreateOfferPojo {
    private int id;
    private String title;

    public CreateOfferPojo(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
