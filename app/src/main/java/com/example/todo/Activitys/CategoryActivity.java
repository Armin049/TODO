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
import com.example.todo.Entity.Category;
import com.example.todo.Entity.Priority;
import com.example.todo.Entity.Todo;
import com.example.todo.Lists.CategoryAdapter;
import com.example.todo.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements CategoryAdapter.OnNoteListenerCat {

    private AppDatabase database;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Category> category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_categories);
        database = AppDatabase.getDatabase(getApplicationContext());
        List<Category> cate = database.categoryDao().getAllCategory();
        recyclerView = findViewById(R.id.recyclerViewCategorie);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new CategoryAdapter(cate,this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override //get the Button_ID and delete the Category with it
    public void onNoteClick(int position) {
        List<Category> cat=database.categoryDao().getAllCategory();
        long id=cat.get(position).getCategory_id();
        List<Todo>todo=database.todoDao().getAllTodos();
        database.categoryDao().deleteByID(id);
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void newCategorie(View view){
        EditText neueCat = findViewById(R.id.neueCategory);
        if (neueCat != null){
            database.categoryDao().addCategory(new Category(neueCat.getText().toString()));
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
