package ie.app.ceolpad.view.musicclass;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;

import ie.app.ceolpad.R;
import ie.app.ceolpad.dao.MusicClassDao;
import ie.app.ceolpad.model.MusicClass;
import ie.app.ceolpad.utils.Config;
import ie.app.ceolpad.view.CreateListener;

/*
 *   Created By Michael Gleeson on 12/11/2020
 *   Copyright (c) 2020 | gleeson.io
 */
public class MusicClassAddFragment extends DialogFragment {

    private static CreateListener createListener;

    private EditText etClassName;
    private Spinner spDay;
    private EditText etTime;
    private Button btnAdd;
    private Button btnCancel;

    private String className = "";
    private String day = "";
    private String time = "";

    public MusicClassAddFragment() {

    }

    public static MusicClassAddFragment newInstance(String title, CreateListener listener){
        createListener = listener;
        MusicClassAddFragment studentCreateDialogFragment = new MusicClassAddFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        studentCreateDialogFragment.setArguments(args);

        studentCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return studentCreateDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_music_class_add, container, false);

        etClassName = view.findViewById(R.id.etClassName);
        spDay = view.findViewById(R.id.spDay);
        etTime = view.findViewById(R.id.etTime);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnCancel = view.findViewById(R.id.btnCancel);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                className = etClassName.getText().toString();
                day = spDay.getSelectedItem().toString();
                time = etTime.getText().toString();

                MusicClass musicClass = new MusicClass(-1, className, day, time);

                MusicClassDao musicClassDao = new MusicClassDao(getContext());

                long id = musicClassDao.insertStudent(musicClass);

                if(id>0){
                    musicClass.setId(id);
                    createListener.onCreated(musicClass);
                    getDialog().dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }

}
