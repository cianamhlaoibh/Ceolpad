package ie.app.ceolpad.view.lesson;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import ie.app.ceolpad.R;


import ie.app.ceolpad.utils.Config;
import ie.app.ceolpad.view.lesson.ui.main.LessonFragment;
import ie.app.ceolpad.view.lesson.ui.main.StudentFragment;
import ie.app.ceolpad.view.lesson.ui.main.ViewPagerAdapter;

public class LessonActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private long classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        classId = getIntent().getLongExtra(Config.MUSIC_CLASS_ID, -1);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), classId);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LessonActivity.this, AddLessonActivity.class);
                startActivity(intent);
            }
        });
    }
}