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
import ie.app.ceolpad.adapter.StudentListRecyclerAdapter;
import ie.app.ceolpad.dao.LessonDao;
import ie.app.ceolpad.dao.StudentDao;
import ie.app.ceolpad.model.Lesson;
import ie.app.ceolpad.model.Student;
import ie.app.ceolpad.utils.Config;
import ie.app.ceolpad.view.RecyclerItemOnClickListener;
import ie.app.ceolpad.view.classinfo.AddStudentActivity;
import ie.app.ceolpad.view.classinfo.ViewEditLessonActivity;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class StudentFragment extends Fragment implements RecyclerItemOnClickListener {
    View v;

    public static RecyclerView recyclerView;
    private StudentListRecyclerAdapter studentListRecyclerAdapter;
    private Context context;
    private StudentDao studentDao;
    private ArrayList<Student> studentList = new ArrayList<>();
    private TextView tvEmptyList;
    private long classId;

    public StudentFragment(long id) {
        classId = id;
    }

    public StudentFragment (){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_student,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvEmptyList = view.findViewById(R.id.tvEmptyList);
        recyclerView = v.findViewById(R.id.rvList);

        studentDao = new StudentDao(getContext());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void viewVisibility() {
        if(studentList.isEmpty())
            tvEmptyList.setVisibility(View.VISIBLE);
        else
            tvEmptyList.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        //lessonListReyclerAdapter.notifyDataSetChanged();
        studentList.clear();
        studentList.addAll(studentDao.getAllStudentByClass(classId));
        studentListRecyclerAdapter = new StudentListRecyclerAdapter(getContext(),studentList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(studentListRecyclerAdapter);
        viewVisibility();

    }

    // Swipe Functionality - https://www.youtube.com/watch?v=rcSNkSJ624U
    Student deletedStudent = null;
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
                    deletedStudent = studentList.get(position);
                    String message = "Are you sure you want to delete " + deletedStudent.getFirstName() + " " + deletedStudent.getSurname();

                    deleteLesson(position);

                    Snackbar.make(recyclerView, message,Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    studentList.add(position, deletedStudent);
                                    studentListRecyclerAdapter.notifyItemInserted(position);
                                    studentDao.insertStudent(deletedStudent, classId);
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
        Student student = studentList.get(position);
        long count = studentDao.deleteStudent(student.getId());

        if (count > 0) {
            studentList.remove(position);
            studentListRecyclerAdapter.notifyItemRemoved(position);
            StudentFragment.this.viewVisibility();
            //Toast.makeText(getContext(), "Lesson deleted successfully", Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(getContext(), "Lesson not deleted. Something wrong!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(Context contx, int position) {
        long id = studentList.get(position).getId();
        Intent intent = new Intent(getContext(), AddStudentActivity.class);
        intent.putExtra(Config.STUDENT_ID, id);
        startActivity(intent);
    }

    @Override
    public void onShare(Context contx, int position) {

    }
}

