package ie.app.ceolpad.model;

import android.net.Uri;

import java.util.Date;

public class Lesson {

    private long lessonId;
    private String lessonDate;
    private String imageUri;
    private String notes;

    public Lesson(){}

    public Lesson(long id,String date, String image, String notes) {
        this.lessonId = id;
        this.lessonDate = date;
        this.imageUri = image;
        this.notes = notes;
    }

    public String getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(String lessonDate) {
        this.lessonDate = lessonDate;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
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
}
