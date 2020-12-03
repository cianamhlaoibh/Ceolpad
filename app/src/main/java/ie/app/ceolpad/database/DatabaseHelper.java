package ie.app.ceolpad.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ie.app.ceolpad.utils.Config;

/*
 *   Reference
 *    - Source - IS4447 SQLite 2 Tables App
 *    - Created By Michael Gleeson
 *
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    // All Static variables
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = Config.DATABASE_NAME;

    // Constructor
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if(databaseHelper==null){
            synchronized (DatabaseHelper.class) {
                if(databaseHelper==null)
                    databaseHelper = new DatabaseHelper(context);
            }
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tables SQL execution
        String CREATE_MUSIC_CLASS_TABLE = "CREATE TABLE " + Config.TABLE_MUSIC_CLASS + "("
                + Config.COLUMN_CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_CLASS_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_CLASS_DAY + " TEXT NOT NULL, " //nullable
                + Config.COLUMN_CLASS_TIME + " TEXT NOT NULL" //nullable
                + ")";

        String CREATE_LESSON_TABLE = "CREATE TABLE " + Config.TABLE_LESSON + "("
                + Config.COLUMN_LESSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_LESSON_DATE + " TEXT NOT NULL, "
                + Config.COLUMN_LESSON_IMAGE + " TEXT, " //nullable
                + Config.COLUMN_LESSON_NOTES + " TEXT NOT NULL,"
                + Config.COLUMN_FK_CLASS_ID + " INTEGER, "
                + "FOREIGN KEY (" + Config.COLUMN_FK_CLASS_ID + ") REFERENCES " + Config.TABLE_MUSIC_CLASS+ "(" + Config.COLUMN_CLASS_ID + ") ON UPDATE CASCADE ON DELETE CASCADE" //deletes all lessons if parent music class is deleted
               + ")";

       // String CREATE_SUBJECT_TABLE = "CREATE TABLE " + Config.TABLE_SUBJECT + "("
        //        + Config.COLUMN_SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
       //         + Config.COLUMN_REGISTRATION_NUMBER + " INTEGER NOT NULL, "
       //         + Config.COLUMN_SUBJECT_NAME + " TEXT NOT NULL, "
       //         + Config.COLUMN_SUBJECT_CODE + " INTEGER NOT NULL, "
       //         + Config.COLUMN_SUBJECT_CREDIT + " REAL, " //nullable
       //         + "FOREIGN KEY (" + Config.COLUMN_REGISTRATION_NUMBER + ") REFERENCES " + Config.TABLE_STUDENT + "(" + Config.COLUMN_STUDENT_REGISTRATION + ") ON UPDATE CASCADE ON DELETE CASCADE, "
      //          + "CONSTRAINT " + Config.STUDENT_SUB_CONSTRAINT + " UNIQUE (" + Config.COLUMN_REGISTRATION_NUMBER + "," + Config.COLUMN_SUBJECT_CODE + ")"
       //         + ")";

        db.execSQL(CREATE_MUSIC_CLASS_TABLE);
        db.execSQL(CREATE_LESSON_TABLE);

        Log.d("IS4447","DB created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_MUSIC_CLASS);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_LESSON);
        // Create tables again
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        //enable foreign key constraints like ON UPDATE CASCADE, ON DELETE CASCADE
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

}
