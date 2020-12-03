package ie.app.ceolpad.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ie.app.ceolpad.R;
import ie.app.ceolpad.model.Lesson;
import ie.app.ceolpad.utils.Config;
import ie.app.ceolpad.view.OnClickLisener;
import ie.app.ceolpad.view.lesson.LessonActivity;
import ie.app.ceolpad.view.lesson.ViewEditLessonActivity;

public class LessonListReyclerAdapter extends RecyclerView.Adapter<LessonListViewHolder> {
    private Context context;
    private List<Lesson> lessonList;
    private OnClickLisener myOnClickListener;

    public LessonListReyclerAdapter(Context context, List<Lesson> lessonList, OnClickLisener myOnClickListener) {
        this.context = context;
        this.lessonList = lessonList;
        this.myOnClickListener = myOnClickListener;
    }

    @Override
    public LessonListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_lesson_list_item, parent, false);
        return new LessonListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LessonListViewHolder holder, final int position) {
        final int listPosition = position;
        final Lesson lesson = lessonList.get(position);

        holder.tvDate.setText(lesson.getLessonDate().toString());

        holder.ibEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnClickListener.onClick(context, position);
            }
        });
    }



    @Override
    public int getItemCount() {
        return lessonList.size();
    }
}
