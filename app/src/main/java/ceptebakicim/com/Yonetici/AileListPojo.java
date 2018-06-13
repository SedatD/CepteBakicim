package ceptebakicim.com.Yonetici;

/**
 * Created by SD
 * on 8.06.2018.
 */

public class AileListPojo {
    private int id,status;
    private String name,oneSignalID,webSignalID;

    public AileListPojo(int id, int status, String name, String oneSignalID, String webSignalID) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.oneSignalID = oneSignalID;
        this.webSignalID = webSignalID;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getOneSignalID() {
        return oneSignalID;
    }

    public String getWebSignalID() {
        return webSignalID;
    }

}
