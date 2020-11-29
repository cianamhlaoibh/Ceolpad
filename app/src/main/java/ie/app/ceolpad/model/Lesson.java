package ie.app.ceolpad.model;

import android.net.Uri;

import java.util.Date;

public class Lesson {

    private Date lessonDate;
    private Uri imageUri;
    private String notes;

    public Lesson(){}

    public Lesson(Date date, Uri image, String notes) {
        this.lessonDate = date;
        this.imageUri = image;
        this.notes = notes;
    }

    public Date getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(Date lessonDate) {
        this.lessonDate = lessonDate;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
