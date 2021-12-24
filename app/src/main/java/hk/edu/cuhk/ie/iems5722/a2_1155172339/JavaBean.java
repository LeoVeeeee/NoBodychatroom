package hk.edu.cuhk.ie.iems5722.a2_1155172339;


public class JavaBean {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    private int id;
    private String content;
    private String time;
    private String name;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "JavaBean{" +
                "content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
