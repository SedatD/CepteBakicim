package ceptebakicim.com.Pojo;

/**
 * Created by SD on 22.02.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class BanaOzelPojo {
    private int serviceID,familyID,interviewID,interviewStatus;
    private String typeTitle,name;

    public BanaOzelPojo(int serviceID, int familyID, int interviewID, int interviewStatus, String typeTitle, String name) {
        this.serviceID = serviceID;
        this.familyID = familyID;
        this.interviewID = interviewID;
        this.interviewStatus = interviewStatus;
        this.typeTitle = typeTitle;
        this.name = name;
    }

    public int getServiceID() {
        return serviceID;
    }

    public int getFamilyID() {
        return familyID;
    }

    public int getInterviewID() {
        return interviewID;
    }

    public int getInterviewStatus() {
        return interviewStatus;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public String getName() {
        return name;
    }

}
