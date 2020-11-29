package ie.app.ceolpad.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ie.app.ceolpad.R;

public class ClassListViewHolder extends RecyclerView.ViewHolder  {

    TextView tvDay;
    TextView tvClassName;
    TextView tvTime;
    ImageButton ibEdit;
    ImageButton ibDelete;


    public ClassListViewHolder(@NonNull View itemView) {
        super(itemView);

        tvDay = itemView.findViewById(R.id.tvDay);
        tvClassName = itemView.findViewById(R.id.tvClassName);
        tvTime = itemView.findViewById(R.id.tvTime);
        ibEdit = itemView.findViewById(R.id.ibEdit);
    }
}
