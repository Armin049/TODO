package com.example.todo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.Entity.Todo;

public class MainActivity extends AppCompatActivity {

    private Todo todo;
    private AppDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        }

    public void createTodo(View view){
        database = AppDatabase.getDatabase(getApplicationContext());
        EditText Titel =findViewById(R.id.Titel);
        EditText Beschreibung =findViewById(R.id.description);
        EditText date =findViewById(R.id.Date);
//        database.todoDao().addTodo(new Todo(Titel.getText().toString(),Beschreibung.getText().toString(),date.getText().toString(),));
//        database.todoDao().addTodo(new Todo("test","test","10.10.2020",));
    }


    public void NewActivity(View view) {
        setContentView(R.layout.activity_detail);
    }

    public void cancel(View view) {
        setContentView(R.layout.activity_main);
    }
}