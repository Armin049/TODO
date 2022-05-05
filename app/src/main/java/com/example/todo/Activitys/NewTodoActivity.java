package com.example.todo.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.AppDatabase;
import com.example.todo.DatePickerFragment;
import com.example.todo.Entity.Category;
import com.example.todo.Entity.Priority;
import com.example.todo.Entity.Todo;
import com.example.todo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewTodoActivity extends AppCompatActivity {

    private AppDatabase database;
    TextView textViewCat;
    boolean [] selectedKategory;
    ArrayList<Integer> CategoryList = new ArrayList<>();
    String[] Categories = new String[]{"test","test2","test3","test4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        database = AppDatabase.getDatabase(getApplicationContext());
        List<Priority> prio = database.priorityDao().getAllPriority();
        List<Category> cat = database.categoryDao().getAllCategory();
        if (prio.size() == 0) {
            database.priorityDao().addPriority(new Priority("Gering"));
            database.priorityDao().addPriority(new Priority("Mittel"));
            database.priorityDao().addPriority(new Priority("Hoch"));
        }
        if (cat.size()==0){
            database.categoryDao().addCategory(new Category("Arbeiten"));
            database.categoryDao().addCategory(new Category("Uni"));
            database.categoryDao().addCategory(new Category("Freizeit"));
            database.categoryDao().addCategory(new Category("Einkaufen"));
        }
//        for (int i=0;i<=cat.size();i++){
//            Categories[i]=cat.get(i).name;
//        }
        textViewCat=findViewById(R.id.selectTVCategory);
        selectedKategory=new boolean[Categories.length];
        textViewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(
                        NewTodoActivity.this
                );
                builder.setTitle("Kategorie Auswählen");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(Categories, selectedKategory, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b){
                            CategoryList.add(i);
                            Collections.sort(CategoryList);
                        }
                        else{
                            CategoryList.remove(i);
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder=new StringBuilder();
                        for (int j=0;j<CategoryList.size();j++){
                            stringBuilder.append(Categories[CategoryList.get(j)]);
                            if (j!=CategoryList.size()-1){
                                stringBuilder.append(", ");
                            }
                        }
                        textViewCat.setText(stringBuilder.toString());
                    }
                });
                builder.setNegativeButton("abbruch", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j=0;j<selectedKategory.length;j++){
                            selectedKategory[j]=false;
                            CategoryList.clear();
                            textViewCat.setText("");
                        }
                    }
                });
                builder.show();
            }
        });
        EditText editText = findViewById(R.id.DateEdit);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        getData();
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

    public void createTodo(View view) {
        database = AppDatabase.getDatabase(getApplicationContext());
        EditText Titel = findViewById(R.id.TitelEdit);
        EditText Beschreibung = findViewById(R.id.descriptionEdit);
        EditText date = findViewById(R.id.DateEdit);
        Spinner spinner = (Spinner) findViewById(R.id.PriorityEdit);
        database.todoDao().addTodo(new Todo(Titel.getText().toString(),
                Beschreibung.getText().toString(), date.getText().toString(), spinner.getSelectedItemId() + 1));    //array starts by 0 but DB with 1 -> +1
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
