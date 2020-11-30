package ie.app.ceolpad.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ie.app.ceolpad.R;
import ie.app.ceolpad.model.Lesson;

public class LessonListReyclerAdapter extends RecyclerView.Adapter<LessonListViewHolder> {
    private Context context;
    private List<Lesson> lessonList;

    public LessonListReyclerAdapter(Context context, List<Lesson> lessonList) {
        this.context = context;
        this.lessonList = lessonList;
    }

    @Override
    public LessonListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_lesson_list_item, parent, false);
        return new LessonListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LessonListViewHolder holder, int position) {
        final int listPosition = position;
        final Lesson lesson = lessonList.get(position);

        holder.tvDate.setText(lesson.getLessonDate().toString());

        holder.ibEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // editSubject(lesson.getId(), listPosition);
            }
        });
    }



    @Override
    public int getItemCount() {
        return lessonList.size();
    }
}
