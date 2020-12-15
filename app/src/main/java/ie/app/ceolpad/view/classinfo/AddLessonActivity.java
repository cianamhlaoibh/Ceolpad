package ie.app.ceolpad.view.classinfo;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import ie.app.ceolpad.R;
import ie.app.ceolpad.dao.LessonDao;
import ie.app.ceolpad.model.Lesson;
import ie.app.ceolpad.utils.Config;
import ie.app.ceolpad.utils.ImageFilePath;

public class AddLessonActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etDate, etNotes;
    Button btnAdd, btnCancel;
    ImageButton ibCamera;
    ImageView ivTune;
    Toolbar toolbar;
    DatePickerDialog picker;
    TextInputLayout tilDate, tilNotes;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    private long classId;
    private String notes, mCurrentPhotoPath, stDate, uri;
    private Date date;
    private Uri selectedImageUri = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etDate = findViewById(R.id.etDate);
        tilDate = findViewById(R.id.tilDate);
        etNotes = findViewById(R.id.etNotes);
        tilNotes = findViewById(R.id.tilNotes);
        ivTune = findViewById(R.id.ivTune);
        ibCamera = findViewById(R.id.ibCamera);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
        toolbar = findViewById(R.id.toolbar);

        ibCamera.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        date = new Date();

        classId = getIntent().getLongExtra(Config.MUSIC_CLASS_ID, -1);
        final String className = getIntent().getStringExtra(Config.MUSIC_CLASS_NAME);
        toolbar.setTitle(className + " Class - Add Lesson");

        //https://www.tutlane.com/tutorial/android/android-datepicker-with-examples

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                final int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddLessonActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                if(dayOfMonth < 10) {
                                    String day = "0" + dayOfMonth;
                                    etDate.setText(year + "/" + (monthOfYear + 1) + "/" + day);
                                }else{
                                    etDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                                }
                            }
                        }, year, month, day);
                //Restricts user to enter a date that is not in the future
                //https://stackoverflow.com/questions/20970963/how-to-disable-future-dates-in-android-date-picker
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
                picker.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibCamera:
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        //permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        //permission already granted
                        pickImageFromGallery();
                    }
                } else {
                    //system os is less then marshmallow
                    pickImageFromGallery();
                }
                break;

            case R.id.btnAdd:

                if(!validateDate() | !validateNote()){
                    return;
                }

                Lesson lesson;
                if(selectedImageUri != null) {
                    uri = selectedImageUri.toString();
                    lesson = new Lesson(-1, stDate, mCurrentPhotoPath, notes, uri);
                }else{
                    lesson = new Lesson(-1, stDate, notes);
                }

                LessonDao lessonDao = new LessonDao(getApplicationContext());

                long id = lessonDao.insertLesson(lesson, classId);

                if (id > 0) {
                    lesson.setLessonId(id);
                }
                //LessonFragment.recyclerView.getAdapter().notifyDataSetChanged();
                finish();

                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }

    //https://devofandroid.blogspot.com/2018/09/pick-image-from-gallery-android-studio.html
    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //set image to image view
            selectedImageUri = data.getData();
            Log.d("IMAGE URI", "onActivityResult: " + selectedImageUri);
            mCurrentPhotoPath = ImageFilePath.getPath(getApplicationContext(), selectedImageUri);

            try {
                InputStream imageStream = getContentResolver().openInputStream(selectedImageUri);
                Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                imageStream.close();
                ivTune.setImageBitmap(yourSelectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // ivTune.setImageURI(data.getData());
            // mCurrentPhotoPath = data.getData().;
            Log.d("PATH", mCurrentPhotoPath);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission was granted
                    pickImageFromGallery();
                } else {
                    //permission was denied
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    //Validation Functions
    private boolean validateDate(){
        String input = etDate.getText().toString();

        if (input.isEmpty()) {
            etDate.setError(getString(R.string.error_lesson_empty));
            return false;
        }else{
            etDate.setError(null);
            tilDate.setErrorEnabled(false);
            stDate = input;
            return true;
        }
    }
    private boolean validateNote(){
        String input = etNotes.getText().toString().trim();
        if (input.isEmpty()) {
            etNotes.setError(getString(R.string.error_note_empty));
            return false;
        }else{
            etNotes.setError(null);
            tilNotes.setErrorEnabled(false);
            notes = input;
            return true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //hide soft keyboard - https://stackoverflow.com/questions/8997225/how-to-hide-android-soft-keyboard-on-edittext
        etDate.setCursorVisible(false);
        etDate.setFocusableInTouchMode(false);
        etDate.setFocusable(false);
    }
}
