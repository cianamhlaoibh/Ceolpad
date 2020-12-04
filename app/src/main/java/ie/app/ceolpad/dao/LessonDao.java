package ie.app.ceolpad.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ie.app.ceolpad.database.DatabaseHelper;
import ie.app.ceolpad.model.Lesson;
import ie.app.ceolpad.model.MusicClass;
import ie.app.ceolpad.utils.Config;

public class LessonDao {

    private Context context;
    //Dependency Injection
    public LessonDao(Context context){
        this.context = context;
    }

    public List<Lesson> getAllLessonById(long fkClassId){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<Lesson> lessonList = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Config.TABLE_LESSON,
                    new String[] {Config.COLUMN_LESSON_ID, Config.COLUMN_LESSON_DATE, Config.COLUMN_LESSON_IMAGE, Config.COLUMN_LESSON_NOTES, Config.COLUMN_LESSON_URI},
                    Config.COLUMN_FK_CLASS_ID + " = ? ",
                    new String[] {String.valueOf(fkClassId)},
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
                    String imageUri = cursor.getString(cursor.getColumnIndex(Config.COLUMN_LESSON_URI));

                    lessonList.add(new Lesson(id, date, image, notes, imageUri));
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

    public Lesson getSingleLessonById(long idnum){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Lesson lesson = null;
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Config.TABLE_LESSON,
                    new String[] {Config.COLUMN_LESSON_ID, Config.COLUMN_LESSON_DATE, Config.COLUMN_LESSON_IMAGE, Config.COLUMN_LESSON_NOTES, Config.COLUMN_LESSON_URI},
                    Config.COLUMN_LESSON_ID + " = ? ",
                    new String[] {String.valueOf(idnum)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                do {
                    //ONLY NEED ID AND DATE
                    int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_LESSON_ID));
                    String date = cursor.getString(cursor.getColumnIndex(Config.COLUMN_LESSON_DATE));
                    //https://beginnersbook.com/2013/04/java-string-to-date-conversion/
                    String image = cursor.getString(cursor.getColumnIndex(Config.COLUMN_LESSON_IMAGE));
                    String notes = cursor.getString(cursor.getColumnIndex(Config.COLUMN_LESSON_NOTES));
                    String imageUri = cursor.getString(cursor.getColumnIndex(Config.COLUMN_LESSON_URI));

                    lesson = new Lesson(id, date, image, notes, imageUri);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return lesson;
    }

    public long insertLesson(Lesson lesson, long fkClassId){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_LESSON_DATE, lesson.getLessonDate());
        contentValues.put(Config.COLUMN_LESSON_IMAGE, lesson.getimagePath());
        contentValues.put(Config.COLUMN_LESSON_NOTES, lesson.getNotes());
        contentValues.put(Config.COLUMN_LESSON_URI, lesson.getImageUri());
        contentValues.put(Config.COLUMN_FK_CLASS_ID, fkClassId);

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_LESSON, null, contentValues);
        } catch (SQLiteException e){
            Log.d("IS4447", "Exception: "+ e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public long updateLesson(long id, String updatedNote){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_LESSON_NOTES, updatedNote);

        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_LESSON, contentValues,
                    Config.COLUMN_LESSON_ID + " = ? ",
                    new String[] {String.valueOf(id)});
        } catch (SQLiteException e){
            Log.d("IS4447", "Exception: "+ e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        return rowCount;
    }

    public long deleteLesson(long lessonId) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(Config.TABLE_LESSON,
                    Config.COLUMN_LESSON_ID + " = ? ",
                    new String[]{ String.valueOf(lessonId)});
        } catch (SQLiteException e){
            Log.d("IS4447", "Exception: "+ e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRowCount;
    }
}
