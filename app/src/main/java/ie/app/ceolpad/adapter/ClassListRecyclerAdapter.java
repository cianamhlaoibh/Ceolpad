package ie.app.ceolpad.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ie.app.ceolpad.R;
import ie.app.ceolpad.dao.MusicClassDao;
import ie.app.ceolpad.model.MusicClass;
import ie.app.ceolpad.utils.Config;
import ie.app.ceolpad.view.UpdateListener;
import ie.app.ceolpad.view.lesson.LessonActivity;
import ie.app.ceolpad.view.musicclass.MusicClassListActivity;
import ie.app.ceolpad.view.musicclass.MusicClassUpdateFragment;

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
                MusicClassUpdateFragment musicClassUpdateFragment = MusicClassUpdateFragment.newInstance(musicClass.getId(), itemPosition, new UpdateListener() {
                    @Override
                    public void onMusicClassUpdated(MusicClass musicClass, int position) {
                        classList.set(position, musicClass);
                        notifyDataSetChanged();
                    }
                });
                musicClassUpdateFragment.show(((MusicClassListActivity) context).getSupportFragmentManager(), Config.UPDATE_MUSIC_CLASS);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LessonActivity.class);
                intent.putExtra(Config.MUSIC_CLASS_ID, musicClass.getId());
                intent.putExtra(Config.MUSIC_CLASS_NAME, musicClass.getClassName());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return  classList.size();
    }

}
