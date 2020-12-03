package ie.app.ceolpad.view.classinfo.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ie.app.ceolpad.R;
import ie.app.ceolpad.view.RecyclerItemOnClickListener;

public class StudentFragment extends Fragment implements RecyclerItemOnClickListener {
    View v;

    public StudentFragment (){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_student,container,false);
        return v;
    }

    @Override
    public void onClick(Context contx, int position) {

    }
}

