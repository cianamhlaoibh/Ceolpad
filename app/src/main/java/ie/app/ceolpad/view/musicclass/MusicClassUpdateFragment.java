package ie.app.ceolpad.view.musicclass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;

import ie.app.ceolpad.R;
import ie.app.ceolpad.dao.MusicClassDao;
import ie.app.ceolpad.model.MusicClass;
import ie.app.ceolpad.utils.Config;
import ie.app.ceolpad.view.UpdateListener;

public class MusicClassUpdateFragment extends DialogFragment {

    private static long id;
    private static int musicClassItemPosition;
    private static UpdateListener updateListener;

    private MusicClass musicClass;
    private MusicClassDao musicClassDao;

    private EditText etClassName;
    private Spinner spDay;
    private EditText etTime;
    private Button btnUpdate;
    private Button btnCancel;

    private String className = "";
    private String day = "";
    private String time = "";



    public MusicClassUpdateFragment() {

    }

    public static MusicClassUpdateFragment newInstance(long idnum, int position, UpdateListener listener){
        id = idnum;
        musicClassItemPosition = position;
        updateListener = listener;
        MusicClassUpdateFragment musicClassUpdateFragment = new MusicClassUpdateFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update music class information");
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
        spDay = view.findViewById(R.id.spDay);
        etTime = view.findViewById(R.id.etTime);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnCancel = view.findViewById(R.id.btnCancel);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

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

                    className = etClassName.getText().toString();
                    day = spDay.getSelectedItem().toString();
                    time = etTime.getText().toString();

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

}
