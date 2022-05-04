package com.example.todo;

import android.app.DatePickerDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Entity.Priority;
import com.example.todo.Entity.Todo;
import com.example.todo.Lists.PriorityAdapter;
import com.example.todo.Lists.TodoAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private AppDatabase database;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public void getTodos() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<Todo> todos = database.todoDao().getAllTodos();
        mAdapter = new TodoAdapter(todos);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = AppDatabase.getDatabase(getApplicationContext());
        List<Priority> cat = database.priorityDao().getAllPriority();
        if (cat.size() == 0) {
            database.priorityDao().addPriority(new Priority("Gering"));
            database.priorityDao().addPriority(new Priority("Mittel"));
            database.priorityDao().addPriority(new Priority("Hoch"));
        }
        getTodos();
    }

    @Override
    protected void onPause() {
        super.onPause();
        database.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Configuration config = getResources().getConfiguration();
        switch (config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                return true;
            default: {
                getMenuInflater().inflate(R.menu.menu, menu);
                return true;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.einstellungen:
//                setContentView(R.layout.);
                return true;
            case R.id.Category:
                setContentView(R.layout.change_categories);
                return true;
            case R.id.Prioritys:
                recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);
                List<Priority> prio = database.priorityDao().getAllPriority();
                mAdapter = new PriorityAdapter(prio);
                recyclerView.setAdapter(mAdapter);
                setContentView(R.layout.change_prioritys);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void createTodo(View view) {
        database = AppDatabase.getDatabase(getApplicationContext());
        EditText Titel = findViewById(R.id.Titel);
        EditText Beschreibung = findViewById(R.id.description);
        EditText date = findViewById(R.id.Date);
        Spinner spinner = (Spinner)findViewById(R.id.Priority);
        database.todoDao().addTodo(new Todo(Titel.getText().toString(), Beschreibung.getText().toString(), date.getText().toString(),1));
        setContentView(R.layout.activity_main);
        getTodos();
    }

    public void edit(long id) {

    }

    public void NewActivity(View view) {
        setContentView(R.layout.activity_detail);
        getData();
        EditText editText=findViewById(R.id.Date);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });
    }

    public void cancel(View view) {
        setContentView(R.layout.activity_main);
        getTodos();
    }

    public void getData() {
        List<String> priority = new ArrayList<String>();
        database = AppDatabase.getDatabase(getApplicationContext());
        List<Priority>prio=database.priorityDao().getAllPriority();
        for (int i=0;i<prio.size();i++){
            priority.add(prio.get(i).name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, priority);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.Priority);
        sItems.setAdapter(adapter);
    }

    public void neuePrio(View view){
        EditText prio = findViewById(R.id.neuePrioritaet);
        database.priorityDao().addPriority(new Priority(prio.getText().toString()));
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        String currentDate= DateFormat.getDateInstance().format(calendar.getTime());
        EditText editText=findViewById(R.id.Date);
        editText.setText(currentDate);
    }
}