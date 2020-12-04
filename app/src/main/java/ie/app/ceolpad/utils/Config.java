package ie.app.ceolpad.utils;

/*
 *   Reference
 *    - Source - IS4447 SQLite 2 Tables App
 *    - Created By Michael Gleeson
 *
 */

public class Config {

    public static final String DATABASE_NAME = "music_class-db";


    //column names of music class table
    public static final String TABLE_MUSIC_CLASS= "music_class";
    public static final String COLUMN_CLASS_ID = "id";
    public static final String COLUMN_CLASS_NAME = "class_name";
    public static final String COLUMN_CLASS_DAY = "day";
    public static final String COLUMN_CLASS_TIME = "time";

    //column names of music class table
    public static final String TABLE_LESSON= "lesson";
    public static final String COLUMN_LESSON_ID = "id";
    public static final String COLUMN_LESSON_DATE = "date";
    public static final String COLUMN_LESSON_IMAGE = "image";
    public static final String COLUMN_LESSON_NOTES = "notes";
    public static final String COLUMN_LESSON_URI = "uri";
    public static final String COLUMN_FK_CLASS_ID = "class_id";

    //column names of student table
    public static final String TABLE_STUDENT = "student";
    public static final String COLUMN_STUDENT_ID = "id";
    public static final String COLUMN_STUDENT_FIRST_NAME = "first_name";
    public static final String COLUMN_STUDENT_SURNAME = "surname";
    public static final String COLUMN_STUDENT_INSTRUMENT = "instrument";
    public static final String COLUMN_STUDENT_REGISTRATION_DATE = "registration_date";
    public static final String COLUMN_STUDENT_EMAIL = "email";

    //others for general purpose key-value pair data
    public static final String TITLE = "title";
    public static final String CREATE_MUSIC_CLASS  = "create_music_class";
    public static final String UPDATE_MUSIC_CLASS = "update_music_class";
    public static final String CREATE_STUDENT = "create_student";
    public static final String UPDATE_STUDENT = "update_student";
    public static final String CREATE_LESSON = "create_lesson";
    public static final String UPDATE_LESONN = "update_lesson";
    public static final String MUSIC_CLASS_ID = "music_class_id";
    public static final String MUSIC_CLASS_NAME = "className";
    public static final String LESSON_ID = "lessonID";
    public static final String STUDENT_ID = "studentID";
}
