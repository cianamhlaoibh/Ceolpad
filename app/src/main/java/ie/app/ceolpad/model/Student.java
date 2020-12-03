package ie.app.ceolpad.model;

import java.util.Date;

public class Student {

    private long id;
    private String firstName;
    private String surname;
    private String registerDate;
    private String instrument;
    private String email;

    public Student(){}

    public Student(long id, String firstName, String surname, String registerDate, String instrument, String email){
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.registerDate = registerDate;
        this.instrument = instrument;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
