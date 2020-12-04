package ie.app.ceolpad.view;

import android.content.Context;

import ie.app.ceolpad.model.Lesson;

public interface RecyclerItemOnClickListener {
    void onClick(Context contx, int position);
    void onShare(Context contx, int position);
}
