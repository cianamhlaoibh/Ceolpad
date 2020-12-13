package ie.app.ceolpad.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ie.app.ceolpad.R;
import ie.app.ceolpad.model.Student;
import ie.app.ceolpad.view.musicclass.UpdateStudentListener;

public class StudentListRecyclerAdapter extends RecyclerView.Adapter<StudentListViewHolder> {

    private Context context;
    private List<Student> studentList;
    private UpdateStudentListener updateStudentListener;

    public StudentListRecyclerAdapter(Context context, List<Student> StudentList, UpdateStudentListener updateStudentListener) {
        this.context = context;
        this.studentList = StudentList;
        this.updateStudentListener = updateStudentListener;
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
                Log.d("output", "onClick: "+student.getId());
                updateStudentListener.updateStudent(student.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
