package hk.edu.cuhk.ie.iems5722.a2_1155172339;

public class ChatroomBean {
    private String roomName;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    @Override
    public String toString() {
        return "ChatroomBean{" +
                "roomName='" + roomName + '\'' +
                ", id=" + id +
                '}';
    }
}
