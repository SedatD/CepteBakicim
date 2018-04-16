package ceptebakicim.com;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by SD on 4.03.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class DummyParentDataItem implements Serializable {
    private String parentName;
    private ArrayList<DummyChildDataItem> childDataItems;
    private int typeId;

    public ArrayList<DummyChildDataItem> getChildDataItems() {
        return childDataItems;
    }

    public void setChildDataItems(ArrayList<DummyChildDataItem> childDataItems) {
        this.childDataItems = childDataItems;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

}
