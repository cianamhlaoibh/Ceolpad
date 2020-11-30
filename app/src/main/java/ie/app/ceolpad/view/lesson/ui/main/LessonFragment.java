package ie.app.ceolpad.view.lesson.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ie.app.ceolpad.R;
import ie.app.ceolpad.adapter.ClassListRecyclerAdapter;
import ie.app.ceolpad.adapter.LessonListReyclerAdapter;
import ie.app.ceolpad.dao.LessonDao;
import ie.app.ceolpad.model.Lesson;
import ie.app.ceolpad.model.MusicClass;

public class LessonFragment extends Fragment {

    RecyclerView recyclerView;
    Context context;
    LessonDao lessonDao;
    private ArrayList<Lesson> lessonList = new ArrayList<>();
    private TextView tvEmptyList;
    private long classId;

    public LessonFragment(long id) {
        classId = id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_lesson, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvEmptyList = view.findViewById(R.id.tvEmptyList);
        lessonDao = new LessonDao(getContext());
        //Retrieve class using dao object
        lessonList.addAll(lessonDao.getAllLessons());
        LessonListReyclerAdapter lessonListReyclerAdapter = new LessonListReyclerAdapter(getContext(),lessonList);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(lessonListReyclerAdapter);

        viewVisibility();

    }

    public void viewVisibility() {
        if(lessonList.isEmpty())
            tvEmptyList.setVisibility(View.VISIBLE);
        else
            tvEmptyList.setVisibility(View.GONE);

    }
}
