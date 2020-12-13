package ie.app.ceolpad.view.musicclass;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import ie.app.ceolpad.view.UpdateMusicClassListener;

public class MusicClassUpdateFragment extends DialogFragment {

    private static long id;
    private static int musicClassItemPosition;
    private static UpdateMusicClassListener updateListener;

    private MusicClass musicClass;
    private MusicClassDao musicClassDao;

    private TextInputLayout tilClassName, tilTime;
    private EditText etClassName;
    private Spinner spDay;
    private EditText etTime;
    private Button btnUpdate;
    private Button btnCancel;
    TimePickerDialog picker;

    private String className = "";
    private String day = "";
    private String time = "";



    public MusicClassUpdateFragment() {

    }

    public static MusicClassUpdateFragment newInstance(long idnum, int position, UpdateMusicClassListener listener){
        id = idnum;
        musicClassItemPosition = position;
        updateListener = listener;
        MusicClassUpdateFragment musicClassUpdateFragment = new MusicClassUpdateFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update Music Class");
        musicClassUpdateFragment.setArguments(args);

        musicClassUpdateFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return musicClassUpdateFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_music_class_update, container, false);

        musicClassDao = new MusicClassDao(getContext());

        etClassName = view.findViewById(R.id.etNotes);
        tilClassName = view.findViewById(R.id.tilNotes);
        spDay = view.findViewById(R.id.spDay);
        etTime = view.findViewById(R.id.etTime);
        tilTime = view.findViewById(R.id.tilTime);
        btnUpdate = view.findViewById(R.id.btnUpdate);
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

        musicClass = musicClassDao.getClassById(id);

        if(musicClass!=null){
            etClassName.setText(musicClass.getClassName());

            String compareValue = String.valueOf(musicClass.getDay());
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.DaysOfWeek, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDay.setAdapter(adapter);
            if (compareValue != null) {
                int spinnerPosition = adapter.getPosition(compareValue);
                spDay.setSelection(spinnerPosition);
            }

            etTime.setText(musicClass.getTime());

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!validateMusicClassName() | !validateTime()){
                        return;
                    }

                    day = spDay.getSelectedItem().toString();
                    musicClass.setClassName(className);
                    musicClass.setDay(day);
                    musicClass.setTime(time);

                    long id = musicClassDao.updateMusicClassInfo(musicClass);

                    if(id>0){
                        updateListener.onMusicClassUpdated(musicClass, musicClassItemPosition);
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

        }
        return view;
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
