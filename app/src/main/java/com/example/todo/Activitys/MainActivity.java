package com.example.todo.Activitys;

import android.app.DatePickerDialog;
import android.content.Intent;
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

import com.example.todo.AppDatabase;
import com.example.todo.DatePickerFragment;
import com.example.todo.Entity.Priority;
import com.example.todo.Entity.Todo;
import com.example.todo.Lists.TodoAdapter;
import com.example.todo.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TodoAdapter.OnNoteListener {

    private AppDatabase database;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Todo> todos;

    public void getTodos() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<Todo> todos = database.todoDao().getAllTodos();
        mAdapter = new TodoAdapter(todos, this);
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

//    @Override
//    protected void onPause() {
//        super.onPause();
//        database.close();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //todo add this to every Activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.einstellungen:
//                setContentView(R.layout.);
                return true;
            case R.id.Category:
                Intent intent = new Intent(this, CategoryActivity.class);
                startActivity(intent);
                return true;
            case R.id.Prioritys:
                intent = new Intent(this, PriorityActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void createTodo(View view) {
        database = AppDatabase.getDatabase(getApplicationContext());
        EditText Titel = findViewById(R.id.TitelEdit);
        EditText Beschreibung = findViewById(R.id.descriptionEdit);
        EditText date = findViewById(R.id.DateEdit);
        Spinner spinner = (Spinner) findViewById(R.id.PriorityEdit);
        database.todoDao().addTodo(new Todo(Titel.getText().toString(),
                Beschreibung.getText().toString(), date.getText().toString(), spinner.getSelectedItemId() + 1));    //array starts by 0 but DB with 1 -> +1
        setContentView(R.layout.activity_main);
        getTodos();
    }

    public void NewActivity(View view) {
        setContentView(R.layout.activity_detail);
        getData();
        EditText editText = findViewById(R.id.DateEdit);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
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
        List<Priority> prio = database.priorityDao().getAllPriority();
        for (int i = 1; i <= prio.size(); i++) {       //starting by 1 and later substracting by 1, used to prevent outOfBound Exception
            priority.add(prio.get(i - 1).name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, priority);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.PriorityEdit);
        sItems.setAdapter(adapter);
    }

    public void neuePrio(View view) {
        EditText prio = findViewById(R.id.neuePrioritaet);
        database.priorityDao().addPriority(new Priority(prio.getText().toString()));
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        EditText editText = findViewById(R.id.DateEdit);
        editText.setText(currentDate);
    }

    @Override
    public void onNoteClick(int position) {
        List<Todo> todos = database.todoDao().getAllTodos();
        Intent intent = new Intent(this, EditTodoActivity.class);
        intent.putExtra("ID", todos.get(position).getId());
        startActivity(intent);
    }
}