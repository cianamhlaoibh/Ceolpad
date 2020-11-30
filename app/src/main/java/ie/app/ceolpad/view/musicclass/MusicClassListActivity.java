package ie.app.ceolpad.view.musicclass;

import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ie.app.ceolpad.R;
import ie.app.ceolpad.adapter.ClassListRecyclerAdapter;
import ie.app.ceolpad.dao.MusicClassDao;
import ie.app.ceolpad.model.Lesson;
import ie.app.ceolpad.model.MusicClass;
import ie.app.ceolpad.model.Student;
import ie.app.ceolpad.utils.Config;
import ie.app.ceolpad.view.CreateListener;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
/*
 *   References
 *    - Source - IS4447 SQLite 2 Tables App
 *    - Created By Michael Gleeson
 *
 *    - Source - Swipe gestures in Recycler View | Android https://www.youtube.com/watch?v=rcSNkSJ624U
 *    - Created By yoursTRULY
 *
 */
public class MusicClassListActivity extends AppCompatActivity implements CreateListener {

    private MusicClassDao musicClassDao = new MusicClassDao(this);

    private List<MusicClass> classList = new ArrayList<>();

    private TextView tvEmptyList;
    private RecyclerView rvList;
    private ClassListRecyclerAdapter classListRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_class_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvEmptyList = findViewById(R.id.tvEmptyList);
        rvList = findViewById(R.id.rvList);

        //Retrieve class using dao object
        classList.addAll(musicClassDao.getAllClasses());

        classListRecyclerAdapter = new ClassListRecyclerAdapter(this, classList);
        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvList.setAdapter(classListRecyclerAdapter);

        viewVisibility();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               MusicClassAddFragment musicClassAddFragment = MusicClassAddFragment.newInstance("Create Music Class", MusicClassListActivity.this);
                musicClassAddFragment.show(getSupportFragmentManager(), Config.CREATE_MUSIC_CLASS);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvList);
    }

    // Swipe Functionality - https://www.youtube.com/watch?v=rcSNkSJ624U
    MusicClass deletedClass = null;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();


            switch (direction) {
                case ItemTouchHelper.LEFT:
                    deletedClass = classList.get(position);
                    String message = "Are you sure you want to delete " + deletedClass.getClassName();

                    //ADD REFERENCE
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MusicClassListActivity.this);
                    alertDialogBuilder.setMessage(message);
                    alertDialogBuilder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    deleteMusicClass(position);
                                }
                            });

                    alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    /*
                    classList.remove(position);
                    classListRecyclerAdapter.notifyItemRemoved(position);
                    Snackbar.make(rvList, message,Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    classList.add(position, deletedClass);
                                    classListRecyclerAdapter.notifyItemInserted(position);
                                }
                            }).show();
                    break;

                     */
                case ItemTouchHelper.RIGHT:

                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            //Swipe Decorator library by Paolo Mantalto - https://github.com/xabaras/RecyclerViewSwipeDecorator
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MusicClassListActivity.this, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void viewVisibility() {
        if(classList.isEmpty())
            tvEmptyList.setVisibility(View.VISIBLE);
        else
            tvEmptyList.setVisibility(View.GONE);

    }
    private void openStudentCreateDialog() {

    }

    private void deleteMusicClass(int position) {
        MusicClass musicClass = classList.get(position);
        long count = musicClassDao.deleteMusicClass(musicClass.getId());

        if(count>0){
            classList.remove(position);
            classListRecyclerAdapter.notifyItemRemoved(position);
            MusicClassListActivity.this.viewVisibility();
            Toast.makeText(MusicClassListActivity.this, "Music Class deleted successfully", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(MusicClassListActivity.this, "Music Class not deleted. Something wrong!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onCreated(MusicClass musicClass) {
        classList.add(musicClass);
        classListRecyclerAdapter.notifyDataSetChanged();
        viewVisibility();
    }

    @Override
    public void onCreated(Lesson lesson) {

    }

    @Override
    public void onCreated(Student student) {

    }
}