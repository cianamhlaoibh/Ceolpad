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

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.regex.Pattern;

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
    TextInputLayout tilFirstName, tilSurname, tilRegDate, tilEmail;

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
        tilFirstName = findViewById(R.id.tilFirstName);
        etSurname = findViewById(R.id.etSurname);
        tilSurname = findViewById(R.id.tilSurname);
        etRegDate = findViewById(R.id.etRegDate);
        tilRegDate = findViewById(R.id.tilRegDate);
        etEmail = findViewById(R.id.etEmail);
        tilEmail = findViewById(R.id.tilEmail);
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

            if(!validateDate() | !validateFirstName() | !validateSurame() | !validateEmail()){
                    return;
            }

                instrument = spInstrument.getSelectedItem().toString();
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

    //Validation Functions
    private boolean validateDate(){
        String input = etRegDate.getText().toString();
        if (input.isEmpty()) {
            etRegDate.setError(getString(R.string.error_date_empty));
            return false;
        }else{
            etRegDate.setError(null);
            tilRegDate.setErrorEnabled(false);
            regDate = input;
            return true;
        }
    }
    private boolean validateFirstName(){
        String input = etFirstName.getText().toString().trim();
        if (input.isEmpty()) {
            etFirstName.setError(getString(R.string.error_fname_empty));
            return false;
        }else{
            etFirstName.setError(null);
            tilFirstName.setErrorEnabled(false);
            firstName = input;
            return true;
        }
    }
    private boolean validateSurame(){
        String input = etSurname.getText().toString().trim();
        if (input.isEmpty()) {
            etSurname.setError(getString(R.string.error_surname_empty));
            return false;
        }else{
            etSurname.setError(null);
            tilSurname.setErrorEnabled(false);
            surname = input;
            return true;
        }
    }
    private boolean validateEmail(){
        String input = etEmail.getText().toString().trim();
        //String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern regex = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        if (input.isEmpty()) {
            etEmail.setError(getString(R.string.error_email_empty));
            return false;
        }else if(!input.matches(regex.toString())) {
            etEmail.setError(getString(R.string.error_email_invalid));
            return false;
        }else{
            etEmail.setError(null);
            tilEmail.setErrorEnabled(false);
            email = input;
            return true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //hide soft keyboard - https://stackoverflow.com/questions/8997225/how-to-hide-android-soft-keyboard-on-edittext
        etRegDate.setCursorVisible(false);
        etRegDate.setFocusableInTouchMode(false);
        etRegDate.setFocusable(false);
    }
}
