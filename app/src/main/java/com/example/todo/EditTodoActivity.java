package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.Entity.Priority;
import com.example.todo.Entity.Todo;

import java.util.ArrayList;
import java.util.List;

public class EditTodoActivity extends AppCompatActivity {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        EditText titel = findViewById(R.id.TitelEdit);
        EditText desc = findViewById(R.id.descriptionEdit);
        EditText date = findViewById(R.id.DateEdit);
        Spinner spinner= findViewById(R.id.PriorityEdit);
        getData();
        Long id;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = null;
            } else {
                id = extras.getLong("ID");
            }
        } else {
            id = (Long) savedInstanceState.getSerializable("ID");
        }
        database = AppDatabase.getDatabase(getApplicationContext());
        List<Todo> todo=database.todoDao().getTodoById(id);
        titel.setText(todo.get(0).Titel);
        desc.setText(todo.get(0).getBeschreibung());
        date.setText(todo.get(0).datetime);
        List<Priority>prio=database.priorityDao().getPriorityByID(todo.get(0).getPriorityId());
        spinner.setSelection((int) prio.get(0).priorityId);
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
        Spinner sItems = (Spinner) findViewById(R.id.PriorityEdit);
        sItems.setAdapter(adapter);
    }

}
