package com.example.todo.Activitys;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.AppDatabase;
import com.example.todo.Entity.Category;
import com.example.todo.Entity.Priority;
import com.example.todo.Entity.Todo;
import com.example.todo.Lists.TodoAdapter;
import com.example.todo.R;
import com.example.todo.TodoDTO;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TodoAdapter.OnNoteListener {

    private AppDatabase database;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    TodoDTO todoDTO;

    public void getTodos() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<Todo>todos = database.todoDao().getAllTodos();
        List<TodoDTO> todoDTO =new ArrayList<>();
        for (int i=0; i<todos.size();i++) {
            todoDTO.add(new TodoDTO(todos.get(i).getId(),todos.get(i).getTitel(),database.priorityDao().getPriorityByID(todos.get(i).priorityId).get(0).name));
        }
        mAdapter = new TodoAdapter(todoDTO, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume(){
        super.onResume();
        getTodos();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = AppDatabase.getDatabase(getApplicationContext());
        List<Priority> prio = database.priorityDao().getAllPriority();
        List<Category> cat = database.categoryDao().getAllCategory();

        getTodos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.einstellungen:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.Category:
                intent = new Intent(this, CategoryActivity.class);
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

    public void NewActivity(View view) {
        Intent intent = new Intent(this, NewTodoActivity.class);
        startActivity(intent);
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