package ie.app.ceolpad.model;

public class Lesson {

    private long lessonId;
    private String lessonDate;
    private String imagePath;
    private String imageUri;
    private String notes;

    public Lesson(){}

    public Lesson(long id,String date, String image, String notes,String imageUri) {
        this.lessonId = id;
        this.lessonDate = date;
        this.imagePath = image;
        this.notes = notes;
        this.setImageUri(imageUri);
    }

    public String getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(String lessonDate) {
        this.lessonDate = lessonDate;
    }

    public String getimagePath() {
        return imagePath;
    }

    public void setimagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getLessonId() {
        return lessonId;
    }

    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
