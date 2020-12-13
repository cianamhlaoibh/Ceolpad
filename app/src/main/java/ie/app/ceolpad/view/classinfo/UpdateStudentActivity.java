package ie.app.ceolpad.view.classinfo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import ie.app.ceolpad.view.musicclass.UpdateStudentListener;

public class UpdateStudentActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etFirstName, etSurname, etRegDate, etEmail;
    Spinner spInstrument, spClass;
    Button btnUpdate, btnCancel;
    Toolbar toolbar;
    DatePickerDialog picker;


    private long classId, studentId;
    private String firstName, surname, regDate, instrument, email;
    private MusicClass selectedClass;
    private Student student;

    private MusicClass[] musicClasses;
    private StudentDao studentDao;

    private MusicClassDao musicClassDao;
    private CustomSpinnerAdapter customSpinnerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        etFirstName = findViewById(R.id.etFirstName);
        etSurname = findViewById(R.id.etSurname);
        etRegDate = findViewById(R.id.etRegDate);
        etEmail = findViewById(R.id.etEmail);
        spInstrument = findViewById(R.id.spInstrument);
        spClass = findViewById(R.id.spClass);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);
        toolbar = findViewById(R.id.toolbar);

        btnUpdate.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        studentId = getIntent().getLongExtra(Config.STUDENT_ID, -1);
        studentDao = new StudentDao(this);
        student = studentDao.getStudentById(studentId);
        initUIFromPerson();

        musicClassDao = new MusicClassDao(this);
        musicClasses = musicClassDao.getAllClassesArray();

        //https://stackoverflow.com/questions/1625249/android-how-to-bind-spinner-to-custom-object-list
        // Initialize the adapter sending the current context
        // Send the simple_spinner_item layout
        // And finally send the Users array (Your data)
        customSpinnerAdapter = new CustomSpinnerAdapter(UpdateStudentActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                musicClasses);
        spClass.setAdapter(customSpinnerAdapter); // Set the custom adapter to the spinner
        // You can create an anonymous listener to handle the event when is selected an spinner item

        for (int i = 0; i < musicClasses.length; i++) {
            if (musicClasses[i].getId() == classId) {
                spClass.setSelection(i);
            }
        }

        spClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                selectedClass = customSpinnerAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });
    }

    private void initUIFromPerson() {
        etFirstName.setText(student.getFirstName());
        etSurname.setText(student.getSurname());
        etRegDate.setText(student.getRegisterDate());
        etEmail.setText(student.getEmail());
        String compareValue = String.valueOf(student.getInstrument());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Instrument, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInstrument.setAdapter(adapter);
        if (compareValue != null) {
            int spinnerPosition = adapter.getPosition(compareValue);
            spInstrument.setSelection(spinnerPosition);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdate:
                firstName = etFirstName.getText().toString();
                surname = etSurname.getText().toString();
                regDate = etRegDate.getText().toString();
                instrument = spInstrument.getSelectedItem().toString();
                email = etEmail.getText().toString();
                classId = selectedClass.getId();

                student.setFirstName(firstName);
                student.setSurname(surname);
                student.setRegisterDate(regDate);
                student.setInstrument(instrument);
                student.setEmail(email);

                long id = studentDao.updateStudent(student, classId);

                if(id>0){
                    finish();
                }

                finish();
                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }
}


