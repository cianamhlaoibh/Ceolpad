package ie.app.ceolpad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ie.app.ceolpad.R;
import ie.app.ceolpad.model.Student;
import ie.app.ceolpad.view.RecyclerItemOnClickListener;

public class StudentListRecyclerAdapter extends RecyclerView.Adapter<StudentListViewHolder> {

    private Context context;
    private List<Student> studentList;
    private RecyclerItemOnClickListener myOnClickListener;

    public StudentListRecyclerAdapter(Context context, List<Student> StudentList, RecyclerItemOnClickListener myOnClickListener) {
        this.context = context;
        this.studentList = StudentList;
        this.myOnClickListener = myOnClickListener;
    }

    @NonNull
    @Override
    public StudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_student_list_item, parent, false);
        return new StudentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListViewHolder holder, final int position) {

        final Student student = studentList.get(position);

        holder.tvName.setText(student.getFirstName() + " " + student.getSurname());
        holder.tvInstrument.setText(student.getInstrument());
        holder.tvEmail.setText(student.getEmail());
        holder.tvRegisterDate.setText(student.getRegisterDate());

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
        return studentList.size();
    }
}
