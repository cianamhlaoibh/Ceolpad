package ie.app.ceolpad.view.classinfo.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import ie.app.ceolpad.R;
import ie.app.ceolpad.adapter.LessonListReyclerAdapter;
import ie.app.ceolpad.dao.LessonDao;
import ie.app.ceolpad.model.Lesson;
import ie.app.ceolpad.utils.Config;
import ie.app.ceolpad.view.RecyclerItemOnClickListener;
import ie.app.ceolpad.view.classinfo.ViewEditLessonActivity;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class LessonFragment extends Fragment implements RecyclerItemOnClickListener {

    public static RecyclerView recyclerView;
    private LessonListReyclerAdapter lessonListReyclerAdapter;
    private Context context;
    private LessonDao lessonDao;
    private ArrayList<Lesson> lessonList = new ArrayList<>();
    private TextView tvEmptyList;
    private long classId;

    public LessonFragment(long id) {
        classId = id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lesson, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvEmptyList = view.findViewById(R.id.tvEmptyList);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvList);

        lessonDao = new LessonDao(getContext());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    public void viewVisibility() {
        if(lessonList.isEmpty())
            tvEmptyList.setVisibility(View.VISIBLE);
        else
            tvEmptyList.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
       //lessonListReyclerAdapter.notifyDataSetChanged();
        lessonList.clear();
        lessonList.addAll(lessonDao.getAllLessonById(classId));
        lessonListReyclerAdapter = new LessonListReyclerAdapter(getContext(),lessonList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(lessonListReyclerAdapter);
        viewVisibility();

    }

    // Swipe Functionality - https://www.youtube.com/watch?v=rcSNkSJ624U
    Lesson deletedLesson = null;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();


            switch (direction) {
                case ItemTouchHelper.LEFT:
                    deletedLesson = lessonList.get(position);
                    String message = "Are you sure you want to delete lesson on " + deletedLesson.getLessonDate();

                    deleteLesson(position);

                    Snackbar.make(recyclerView, message,Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    lessonList.add(position, deletedLesson);
                                    lessonListReyclerAdapter.notifyItemInserted(position);
                                    lessonDao.insertLesson(deletedLesson, classId);
                                    viewVisibility();
                                }
                            }).show();
                    break;

                case ItemTouchHelper.RIGHT:
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            //Swipe Decorator library by Paolo Mantalto - https://github.com/xabaras/RecyclerViewSwipeDecorator
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };



    private void deleteLesson(int position) {
        Lesson lesson = lessonList.get(position);
        long count = lessonDao.deleteLesson(lesson.getLessonId());

        if (count > 0) {
            lessonList.remove(position);
            lessonListReyclerAdapter.notifyItemRemoved(position);
            LessonFragment.this.viewVisibility();
            //Toast.makeText(getContext(), "Lesson deleted successfully", Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(getContext(), "Lesson not deleted. Something wrong!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(Context contx, int position) {
        long id = lessonList.get(position).getLessonId();
        Intent intent = new Intent(getContext(), ViewEditLessonActivity.class);
        intent.putExtra(Config.LESSON_ID, id);
        startActivity(intent);
    }
}
