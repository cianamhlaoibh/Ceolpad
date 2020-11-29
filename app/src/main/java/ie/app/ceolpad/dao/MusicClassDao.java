package ie.app.ceolpad.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ie.app.ceolpad.database.DatabaseHelper;
import ie.app.ceolpad.model.MusicClass;
import ie.app.ceolpad.utils.Config;

public class MusicClassDao {

    private Context context;
    //Dependency Injection
    public MusicClassDao(Context context){
        this.context = context;
    }

    public long insertStudent(MusicClass musicClass){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_CLASS_NAME, musicClass.getClassName());
        contentValues.put(Config.COLUMN_CLASS_DAY, musicClass.getDay());
        contentValues.put(Config.COLUMN_CLASS_TIME, musicClass.getTime());

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_MUSIC_CLASS, null, contentValues);
        } catch (SQLiteException e){
            Log.d("IS4447", "Exception: "+ e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }


    public List<MusicClass> getAllClasses(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            //cursor = sqLiteDatabase.query(Config.TABLE_MUSIC_CLASS, null, null, null, null, null, Config.COLUMN_CLASS_DAY, null);


             // If you want to execute raw query then uncomment below 2 lines. And comment out above line.

             String SELECT_QUERY = String.format("SELECT * FROM " + Config.TABLE_MUSIC_CLASS);
                                                   // + " ORDER BY CASE WHEN " + Config.COLUMN_CLASS_DAY + " = 'Monday' THEN 1 "
                                                   // + " WHEN " + Config.COLUMN_CLASS_DAY + " = 'Tuesday' THEN 2 "
                                                   //  + " WHEN " + Config.COLUMN_CLASS_DAY + " = 'Wednesday' THEN 3 "
                                                   //  + " WHEN " + Config.COLUMN_CLASS_DAY + " = 'Thursday' THEN 4 "
                                                   //  + " WHEN " + Config.COLUMN_CLASS_DAY + " = 'Friday' THEN 5 "
                                                   //  + " WHEN " + Config.COLUMN_CLASS_DAY + " = 'Saturday' THEN 6 "
                                                   //  + " WHEN " + Config.COLUMN_CLASS_DAY + " = 'Sunday' THEN 7 ENDS ASC

             cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);


            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<MusicClass> studentList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_CLASS_ID));
                        String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_CLASS_NAME));
                        String day = cursor.getString(cursor.getColumnIndex(Config.COLUMN_CLASS_DAY));
                        String time = cursor.getString(cursor.getColumnIndex(Config.COLUMN_CLASS_TIME));

                        studentList.add(new MusicClass(id, name, day, time));
                    }   while (cursor.moveToNext());

                    return studentList;
                }
        } catch (Exception e){
            Log.d("IS4447", "Exception: "+ e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

}
