package ie.app.ceolpad.view;

import ie.app.ceolpad.model.Lesson;
import ie.app.ceolpad.model.MusicClass;
import ie.app.ceolpad.model.Student;

public interface CreateListener {
    void onCreated(MusicClass musicClass);
    void onCreated(Lesson lesson);
    void onCreated(Student student);
}
