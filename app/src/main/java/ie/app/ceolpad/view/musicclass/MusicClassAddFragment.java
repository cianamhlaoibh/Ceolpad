package ie.app.ceolpad.view.musicclass;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

import ie.app.ceolpad.R;
import ie.app.ceolpad.dao.MusicClassDao;
import ie.app.ceolpad.model.MusicClass;
import ie.app.ceolpad.utils.Config;
import ie.app.ceolpad.view.CreateClassListener;

/*
 *   Created By Michael Gleeson on 12/11/2020
 *   Copyright (c) 2020 | gleeson.io
 *
 * https://www.tutlane.com/tutorial/android/android-timepicker-with-examples
 * https://www.youtube.com/watch?v=qcDlcITNqnE
 */
public class MusicClassAddFragment extends DialogFragment {

    private static CreateClassListener createListener;

    private TextInputLayout tilClassName, tilTime;
    private EditText etClassName;
    private Spinner spDay;
    private EditText etTime;
    private Button btnAdd;
    private Button btnCancel;
    TimePickerDialog picker;

    private String className = "";
    private String day = "";
    private String time = "";

    public MusicClassAddFragment() {

    }

    public static MusicClassAddFragment newInstance(String title, CreateClassListener listener){
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

        etClassName = view.findViewById(R.id.etNotes);
        tilClassName = view.findViewById(R.id.tilNotes);
        spDay = view.findViewById(R.id.spDay);
        etTime = view.findViewById(R.id.etTime);
        tilTime = view.findViewById(R.id.tilTime);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnCancel = view.findViewById(R.id.btnCancel);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        //https://www.tutlane.com/tutorial/android/android-timepicker-with-examples
        etTime.setInputType(InputType.TYPE_NULL);
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                etTime.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validateMusicClassName() | !validateTime()){
                    return;
                }

                day = spDay.getSelectedItem().toString();
                //time = etTime.getText().toString();

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

    //Validation Functions
    private boolean validateMusicClassName(){
        String input = etClassName.getText().toString().trim();

        if (input.isEmpty()) {
            etClassName.setError("Class name can not be empty");
            return false;
        }else{
            etClassName.setError(null);
            tilClassName.setErrorEnabled(false);
            className = input;
            return true;
        }
    }
    private boolean validateTime(){
        String input = etTime.getText().toString();

        if (input.isEmpty()) {
            etTime.setError("Time can not be empty");
            return false;
        }else{
            etTime.setError(null);
            tilTime.setErrorEnabled(false);
            time = input;
            return true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //hide soft keyboard - https://stackoverflow.com/questions/8997225/how-to-hide-android-soft-keyboard-on-edittext
        etTime.setCursorVisible(false);
        etTime.setFocusableInTouchMode(false);
        etTime.setFocusable(false);
    }
}
