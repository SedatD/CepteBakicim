package ceptebakicim.com;

/**
 * Created by SD on 4.03.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class DummyChildDataItem {
    private String childName;
    private int childId;
    private String regDate;

    public DummyChildDataItem() {
    }

    public DummyChildDataItem(int childId, String childName, String regDate) {
        this.childId = childId;
        this.childName = childName;
        this.regDate = regDate;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}
