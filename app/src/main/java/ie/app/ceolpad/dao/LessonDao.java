package ie.app.ceolpad.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ie.app.ceolpad.database.DatabaseHelper;
import ie.app.ceolpad.model.Lesson;
import ie.app.ceolpad.utils.Config;

public class LessonDao {

    private Context context;
    //Dependency Injection
    public LessonDao(Context context){
        this.context = context;
    }

    public List<Lesson> getAllLessonById(long idnum){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<Lesson> lessonList = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Config.TABLE_LESSON,
                    new String[] {Config.COLUMN_LESSON_ID, Config.COLUMN_LESSON_DATE, Config.COLUMN_LESSON_IMAGE, Config.COLUMN_LESSON_NOTES},
                    Config.COLUMN_LESSON_ID + " = ? ",
                    new String[] {String.valueOf(idnum)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                lessonList = new ArrayList<>();
                do {
                    //ONLY NEED ID AND DATE
                    int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_LESSON_ID));
                    String date = cursor.getString(cursor.getColumnIndex(Config.COLUMN_LESSON_DATE));
                    //https://beginnersbook.com/2013/04/java-string-to-date-conversion/

                    String image = cursor.getString(cursor.getColumnIndex(Config.COLUMN_LESSON_IMAGE));
                    String notes = cursor.getString(cursor.getColumnIndex(Config.COLUMN_LESSON_NOTES));

                    lessonList.add(new Lesson(id, date, image, notes));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return lessonList;
    }

    public List<Lesson> getAllLessons(){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<Lesson> lessonList = new ArrayList<>();
        lessonList.add(new Lesson(1, "12/12/12", "hello", "Hey there"));

        return lessonList;
    }
}
