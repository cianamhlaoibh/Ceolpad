package ie.app.ceolpad.model;

public class MusicClass {

    private long id;
    private String className;
    private String day;
    private String time;

    public MusicClass(){}

    public MusicClass(long id,String className, String day, String time) {
        this.setId(id);
        this.setClassName(className);
        this.setDay(day);
        this.setTime(time);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
