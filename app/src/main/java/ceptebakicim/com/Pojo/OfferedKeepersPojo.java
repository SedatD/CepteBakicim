package ceptebakicim.com.Pojo;

/**
 * Created by SD on 27.01.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class OfferedKeepersPojo {
    private int serviceID,interviewID,caryID;
    private String name, durum;

    public OfferedKeepersPojo(int serviceID, int interviewID, String name, String durum, int caryID) {
        this.serviceID = serviceID;
        this.interviewID = interviewID;
        this.name = name;
        this.durum = durum;
        this.caryID = caryID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public int getInterviewID() {
        return interviewID;
    }

    public String getName() {
        return name;
    }

    public String getDurum() {
        return durum;
    }

    public int getCaryID() {
        return caryID;
    }

}
