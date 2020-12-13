package ie.app.ceolpad.view.classinfo;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Date;

import ie.app.ceolpad.R;
import ie.app.ceolpad.dao.LessonDao;
import ie.app.ceolpad.model.Lesson;
import ie.app.ceolpad.utils.Config;

public class ViewEditLessonActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ImageView ivTune, ivDone;
    private EditText etNote;

    private LessonDao lessonDao;

    Lesson lesson;
    String notes, imagePath, className, stDate, updatedNote;
    Long lessonId;
    Date date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_lesson);

        toolbar = findViewById(R.id.toolbar);
        ivTune = findViewById(R.id.ivTune);
        ivDone = findViewById(R.id.ivDone);
        etNote = findViewById(R.id.etNote);

        ivDone.setOnClickListener(this);

        lessonDao = new LessonDao(getApplicationContext());
        lessonId = getIntent().getLongExtra(Config.LESSON_ID, -1);
        lesson = lessonDao.getSingleLessonById(lessonId);

        if(lesson!=null){
            stDate = lesson.getLessonDate();
            notes = lesson.getNotes();
            imagePath = lesson.getimagePath();

            toolbar.setTitle("Lesson - " + lesson.getLessonDate());
            etNote.setText(notes);

            //https://alvinalexander.com/source-code/android/android-how-load-image-file-and-set-imageview/
            ivTune.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //updatedNote = etNote.getText().toString();
       // lessonDao.updateLesson(lessonId, updatedNote);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ivDone) {
                updatedNote = etNote.getText().toString();
                lessonDao.updateLesson(lessonId, updatedNote);
                finish();
          //  Intent intent = new Intent(ViewEditLessonActivity.this, ClassInfoActivity.class);
          //  intent.putExtra(Config.MUSIC_CLASS_ID, lesson.);
         //   intent.putExtra(Config.MUSIC_CLASS_NAME, musicClass.getClassName());
          //  context.startActivity(intent);
        }
    }
}
