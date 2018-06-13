package ceptebakicim.com.Pojo;

import org.json.JSONObject;

/**
 * Created by SD on 22.02.2018.
 * dilmacsedat@gmail.com
 * :)
 */

public class BanaOzelPojo {
    private boolean isTum;
    private String typeTitle,name;
    private JSONObject jsonObject;

    public BanaOzelPojo(boolean isTum, String typeTitle, String name, JSONObject jsonObject) {
        this.isTum = isTum;
        this.typeTitle = typeTitle;
        this.name = name;
        this.jsonObject = jsonObject;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public String getName() {
        return name;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public boolean isTum() {
        return isTum;
    }

}
