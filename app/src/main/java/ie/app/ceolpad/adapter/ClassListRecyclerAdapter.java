package ie.app.ceolpad.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ie.app.ceolpad.R;
import ie.app.ceolpad.dao.MusicClassDao;
import ie.app.ceolpad.model.MusicClass;

public class ClassListRecyclerAdapter extends RecyclerView.Adapter<ClassListViewHolder>{

    private Context context;
    private List<MusicClass> classList;
    private MusicClassDao musicClassDao;

    public ClassListRecyclerAdapter(Context context, List<MusicClass> classList) {
        this.context = context;
        this.classList = classList;
        musicClassDao = new MusicClassDao(context);
    }

    @NonNull
    @Override
    public ClassListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_class_list_item, parent, false);
        return new ClassListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClassListViewHolder holder, int position) {
        final int itemPosition = position;
        final MusicClass musicClass = classList.get(position);

        holder.tvClassName.setText(musicClass.getClassName());
        holder.tvDay.setText(String.valueOf(musicClass.getDay()));
        holder.tvTime.setText(musicClass.getTime());




        holder.ibEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(context, SubjectListActivity.class);
                //intent.putExtra(Config.STUDENT_REGISTRATION, student.getRegistrationNumber());
                //context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return  classList.size();
    }
}
