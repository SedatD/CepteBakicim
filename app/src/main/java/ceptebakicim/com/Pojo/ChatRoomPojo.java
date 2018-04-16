package ceptebakicim.com.Pojo;

/**
 * Created by Sedat on 1.04.2018.
 */

public class ChatRoomPojo {
    private int id;
    private String roomName;

    public ChatRoomPojo(int id, String roomName) {
        this.id = id;
        this.roomName = roomName;
    }

    public int getId() {
        return id;
    }

    public String getRoomName() {
        return roomName;
    }
}
