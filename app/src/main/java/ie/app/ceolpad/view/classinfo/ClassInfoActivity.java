package ie.app.ceolpad.view.classinfo;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import ie.app.ceolpad.R;


import ie.app.ceolpad.utils.Config;
import ie.app.ceolpad.view.classinfo.ui.main.ViewPagerAdapter;

//https://stackoverflow.com/questions/31415742/how-to-change-floatingactionbutton-between-tabs

public class ClassInfoActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TextView tvTitle;
    private FloatingActionButton fabAddLesson, fabAddStudent;
    private long classId;
    String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        classId = getIntent().getLongExtra(Config.MUSIC_CLASS_ID, -1);
        className = getIntent().getStringExtra(Config.MUSIC_CLASS_NAME);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), classId);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(onTabSelectedListener);
        viewPager.addOnPageChangeListener(onPageChangeListener);

        fabAddLesson = findViewById(R.id.fabAddLesson);
        fabAddLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassInfoActivity.this, AddLessonActivity.class);
                intent.putExtra(Config.MUSIC_CLASS_ID, classId);
                intent.putExtra(Config.MUSIC_CLASS_NAME, className);
                startActivity(intent);
            }
        });
        fabAddStudent = findViewById(R.id.fabAddStudent);
        fabAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassInfoActivity.this, AddStudentActivity.class);
                intent.putExtra(Config.MUSIC_CLASS_ID, classId);
                intent.putExtra(Config.MUSIC_CLASS_NAME, className);
                startActivity(intent);
            }
        });

        tvTitle = findViewById(R.id.title);
        tvTitle.setText(className  + " Class Info");

        animateFab(0);
    }

    //https://stackoverflow.com/questions/31415742/how-to-change-floatingactionbutton-between-tabs
    private void animateFab(int position) {
        switch (position) {
            case 0:
                fabAddLesson.show();
                fabAddStudent.hide();
                break;
            case 1:
                fabAddStudent.show();
                fabAddLesson.hide();
                break;
        }
    }

    TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            animateFab(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            animateFab(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


}