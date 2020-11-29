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

    //column names of subject table
    public static final String TABLE_SUBJECT = "subject";
    public static final String COLUMN_SUBJECT_ID = "_id";
    public static final String COLUMN_REGISTRATION_NUMBER = "fk_registration_no";
    public static final String COLUMN_SUBJECT_NAME = "name";
    public static final String COLUMN_SUBJECT_CODE = "subject_code";
    public static final String COLUMN_SUBJECT_CREDIT = "credit";
    public static final String STUDENT_SUB_CONSTRAINT = "student_sub_unique";

    //others for general purpose key-value pair data
    public static final String TITLE = "title";
    public static final String CREATE_MUSIC_CLASS  = "create_music_class";
    public static final String UPDATE_MUSIC_CLASS = "update_music_class";
    public static final String CREATE_STUDENT = "create_student";
    public static final String UPDATE_STUDENT = "update_student";
    public static final String CREATE_SUBJECT = "create_subject";
    public static final String UPDATE_SUBJECT = "update_subject";
    public static final String STUDENT_REGISTRATION = "student_registration";
}
