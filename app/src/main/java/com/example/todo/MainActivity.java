package com.example.todo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Entity.Category;
import com.example.todo.Entity.Priority;
import com.example.todo.Entity.Todo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppDatabase database;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    myAdapter adapter;

    public void getTodos(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<Todo> todos=database.todoDao().getAllTodos();
        mAdapter = new myAdapter(todos);
        recyclerView.setAdapter(mAdapter);
        database.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = AppDatabase.getDatabase(getApplicationContext());
        List<Category> cat=database.categoryDao().getAllCategory();
        if (cat.size()==0){
            database.categoryDao().addCategory(new Category("Gering"));
            database.categoryDao().addCategory(new Category("Mittel"));
            database.categoryDao().addCategory(new Category("Hoch"));
        }
        getTodos();
        }

    public void createTodo(View view){
        database = AppDatabase.getDatabase(getApplicationContext());
        EditText Titel =findViewById(R.id.Titel);
        EditText Beschreibung =findViewById(R.id.description);
        EditText date =findViewById(R.id.Date);
            database.todoDao().addTodo(new Todo(Titel.getText().toString(), Beschreibung.getText().toString(), date.getText().toString()));
            setContentView(R.layout.activity_main);
            getTodos();
    }

    public void edit(long id){
        System.out.println("main: "+id);

    }

    public void NewActivity(View view) {
        setContentView(R.layout.activity_detail);
    }

    public void cancel(View view) {
        setContentView(R.layout.activity_main);
        getTodos();
    }

    public void getData()
    {
        List<String> priority = new ArrayList<String>();
        database = AppDatabase.getDatabase(getApplicationContext());
        List<Priority>prio= database.priorityDao().getAllPriority();
        for (int i= 0;i<prio.size();i++){
             priority.add(prio.get(i).name);
        }
    }
}