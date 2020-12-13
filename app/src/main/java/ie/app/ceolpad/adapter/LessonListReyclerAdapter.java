package ie.app.ceolpad.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ie.app.ceolpad.R;
import ie.app.ceolpad.model.Lesson;
import ie.app.ceolpad.view.RecyclerItemOnClickListener;

public class LessonListReyclerAdapter extends RecyclerView.Adapter<LessonListViewHolder> {
    private Context context;
    private List<Lesson> lessonList;
    private RecyclerItemOnClickListener myOnClickListener;

    public LessonListReyclerAdapter(Context context, List<Lesson> lessonList, RecyclerItemOnClickListener myOnClickListener) {
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

        final Lesson lesson = lessonList.get(position);

        holder.tvDate.setText(lesson.getLessonDate().toString());
        holder.tvPreview.setText(lesson.getNotes());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnClickListener.onClick(context, position);
            }
        });

        holder.ibShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnClickListener.onShare(context,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }
}
