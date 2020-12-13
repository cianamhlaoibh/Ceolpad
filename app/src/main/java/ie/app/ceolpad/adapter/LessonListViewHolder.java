package ie.app.ceolpad.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import ie.app.ceolpad.R;

public class LessonListViewHolder extends RecyclerView.ViewHolder {
    TextView tvDate;
    TextView tvPreview;
    ImageView ibShare;

    public LessonListViewHolder(View itemView) {
        super(itemView);

        tvDate = itemView.findViewById(R.id.tvDate);
        tvPreview = itemView.findViewById(R.id.tvPreview);
        ibShare = itemView.findViewById(R.id.ibShare);
    }
}
