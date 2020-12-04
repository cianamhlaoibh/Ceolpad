package ie.app.ceolpad.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import ie.app.ceolpad.R;

public class StudentListViewHolder extends RecyclerView.ViewHolder {

    TextView tvName, tvEmail, tvRegisterDate, tvInstrument;
    ImageView ibEdit;

    public StudentListViewHolder(View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.tvName);
        tvEmail = itemView.findViewById(R.id.tvEmail);
        tvRegisterDate = itemView.findViewById(R.id.tvRegisterDate);
        tvInstrument = itemView.findViewById(R.id.tvInstrument);
        ibEdit = itemView.findViewById(R.id.ibShare);

    }
}