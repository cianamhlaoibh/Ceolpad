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
import ie.app.ceolpad.model.Student;
import ie.app.ceolpad.utils.Config;

public class StudentDao {

    private Context context;

    public StudentDao(Context context){
        this.context = context;
    }

    public long insertStudent(Student student){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_STUDENT_FIRST_NAME, student.getFirstName());
        contentValues.put(Config.COLUMN_STUDENT_SURNAME, student.getSurname());
        contentValues.put(Config.COLUMN_STUDENT_INSTRUMENT, student.getInstrument());
        contentValues.put(Config.COLUMN_STUDENT_REGISTRATION_DATE, student.getRegisterDate());
        contentValues.put(Config.COLUMN_STUDENT_EMAIL, student.getEmail());

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_STUDENT, null, contentValues);
        } catch (SQLiteException e){
            Log.d("IS4447", "Exception: "+ e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<Student> getAllStudentByClass(long fkClassId){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_STUDENT, null,
                    Config.COLUMN_LESSON_ID + " = ? ",
                    new String[] {String.valueOf(fkClassId)},  null, null, null, null);

            /**
             // If you want to execute raw query then uncomment below 2 lines. And comment out above line.

             String SELECT_QUERY = String.format("SELECT %s, %s, %s, %s, %s FROM %s", Config.COLUMN_STUDENT_ID, Config.COLUMN_STUDENT_NAME, Config.COLUMN_STUDENT_REGISTRATION, Config.COLUMN_STUDENT_EMAIL, Config.COLUMN_STUDENT_PHONE, Config.TABLE_STUDENT);
             cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);
             */

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<Student> studentList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_STUDENT_ID));
                        String firstName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_FIRST_NAME));
                        String surame = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_SURNAME));
                        String email = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_EMAIL));
                        String instrument = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_INSTRUMENT));
                        String regDate = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_REGISTRATION_DATE));

                        studentList.add(new Student(id, firstName, surame, regDate, instrument, email));
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
