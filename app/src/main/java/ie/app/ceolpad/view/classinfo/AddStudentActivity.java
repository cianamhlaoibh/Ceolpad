package ie.app.ceolpad.view.classinfo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

import ie.app.ceolpad.R;
import ie.app.ceolpad.adapter.CustomSpinnerAdapter;
import ie.app.ceolpad.dao.MusicClassDao;
import ie.app.ceolpad.dao.StudentDao;
import ie.app.ceolpad.model.MusicClass;
import ie.app.ceolpad.model.Student;
import ie.app.ceolpad.utils.Config;

public class AddStudentActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etFirstName, etSurname, etRegDate, etEmail;
    Spinner spInstrument, spClass;
    Button btnAdd, btnCancel;
    Toolbar toolbar;
    DatePickerDialog picker;


    private long classId;
    private String firstName, surname, regDate, instrument, email;
    private MusicClass selectedClass;

    private MusicClass[] musicClasses;
    private StudentDao studentDao;
    private MusicClassDao musicClassDao;
    private CustomSpinnerAdapter customSpinnerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        etFirstName = findViewById(R.id.etFirstName);
        etSurname = findViewById(R.id.etSurname);
        etRegDate = findViewById(R.id.etRegDate);
        etEmail = findViewById(R.id.etEmail);
        spInstrument = findViewById(R.id.spInstrument);
        spClass = findViewById(R.id.spClass);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
        toolbar = findViewById(R.id.toolbar);

        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        classId = getIntent().getLongExtra(Config.MUSIC_CLASS_ID, -1);
        final String className = getIntent().getStringExtra(Config.MUSIC_CLASS_NAME);
        toolbar.setTitle(className + " Class - Add Student");

        studentDao = new StudentDao(this);

        musicClassDao = new MusicClassDao(this);
        musicClasses = musicClassDao.getAllClassesArray();

        //https://stackoverflow.com/questions/1625249/android-how-to-bind-spinner-to-custom-object-list
        // Initialize the adapter sending the current context
        // Send the simple_spinner_item layout
        // And finally send the Users array (Your data)
        customSpinnerAdapter = new CustomSpinnerAdapter(AddStudentActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                musicClasses);
        spClass.setAdapter(customSpinnerAdapter); // Set the custom adapter to the spinner
        // You can create an anonymous listener to handle the event when is selected an spinner item

        for(int i = 0; i < musicClasses.length; i++){
            if(musicClasses[i].getId() == classId){
                spClass.setSelection(i);
            }
        }

        spClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                selectedClass = customSpinnerAdapter.getItem(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });

        //https://www.tutlane.com/tutorial/android/android-datepicker-with-examples
        etRegDate.setInputType(InputType.TYPE_NULL);
        etRegDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddStudentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                etRegDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                //Restricts user to enter a date that is not in the future
                //https://stackoverflow.com/questions/20970963/how-to-disable-future-dates-in-android-date-picker
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
                picker.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                firstName = etFirstName.getText().toString();
                surname = etSurname.getText().toString();
                regDate = etRegDate.getText().toString();
                instrument = spInstrument.getSelectedItem().toString();
                email = etEmail.getText().toString();
                classId = selectedClass.getId();

                Student newStudent = new Student(-1, firstName, surname, regDate, instrument, email);

                long id = studentDao.insertStudent(newStudent, classId);

                finish();
                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }
}
