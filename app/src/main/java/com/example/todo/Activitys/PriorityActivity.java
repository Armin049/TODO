package com.example.todo.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.AppDatabase;
import com.example.todo.Entity.Priority;
import com.example.todo.Entity.Todo;
import com.example.todo.Lists.PriorityAdapter;
import com.example.todo.R;

import java.util.ArrayList;
import java.util.List;

public class PriorityActivity extends AppCompatActivity implements PriorityAdapter.OnNoteListenerPrio {

    private AppDatabase database;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Priority> prio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_prioritys);
        database = AppDatabase.getDatabase(getApplicationContext());
        List<Priority> prio = database.priorityDao().getAllPriority();
        recyclerView = findViewById(R.id.recyclerViewPrio);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new PriorityAdapter(prio, this);
        recyclerView.setAdapter(mAdapter);
    }

    //deletes the selected Priority
    @Override
    public void onNoteClick(int position) {
        List<Priority> prios=database.priorityDao().getAllPriority();
        long id=prios.get(position).getPriorityId();
        List<Todo>todo=database.todoDao().getAllTodos();
        for (int i=0;i<todo.size();i++){
            if (todo.get(i).priorityId==id){
                Toast.makeText(getApplicationContext(),"Bitte entferne die Priorität erst aus allen Todos",Toast.LENGTH_SHORT).show();//to prevent Database errors
                break;
            }
        }
        database.priorityDao().deleteById(id);
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    //creates an new Priority
    public void neuePrio(View view) {
        EditText neuePrio = findViewById(R.id.neuePrioritaet);
        if (neuePrio != null) {
            database.priorityDao().addPriority(new Priority(neuePrio.getText().toString()));
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
}