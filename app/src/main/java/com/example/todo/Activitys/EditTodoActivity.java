package com.example.todo.Activitys;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.AppDatabase;
import com.example.todo.DatePickerFragment;
import com.example.todo.Entity.Category;
import com.example.todo.Entity.CategoryTodo;
import com.example.todo.Entity.Priority;
import com.example.todo.Entity.Todo;
import com.example.todo.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class EditTodoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private AppDatabase database;
    private long TodoID;
    private RecyclerView.Adapter mAdapter;
    TextView textViewCat;
    boolean[] selectedKategory;
    ArrayList<Integer> CategoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        EditText titel = findViewById(R.id.TitelEdit);
        EditText desc = findViewById(R.id.descriptionEdit);
        EditText date = findViewById(R.id.DateEdit);
        Spinner spinner= findViewById(R.id.PriorityEdit);
        TextView tv = findViewById(R.id.selectTVCategory);
        getData();
        //get all Values from the Database where the ID is X
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
        TodoID=id;
        database = AppDatabase.getDatabase(getApplicationContext());
        List<Todo> todo=database.todoDao().getTodoById(id);
        titel.setText(todo.get(0).Titel);
        desc.setText(todo.get(0).getBeschreibung());
        date.setText(todo.get(0).datetime);
        List<Category> cat_ID=database.categoryTodoDao().getCategoryTodoById(id);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<cat_ID.size();i++) {
            stringBuilder.append(database.categoryDao().getCategoryByID(cat_ID.get(i).getCategory_id()).get(0).getName());
            if (i != cat_ID.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        tv.setText(stringBuilder.toString());

        List<Priority>prio=database.priorityDao().getPriorityByID(todo.get(0).getPriorityId());
        spinner.setSelection((int) prio.get(0).priorityId-1);   //-1 because the DB starts with 1 and the Array with 0
        EditText editText = findViewById(R.id.DateEdit);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        //same as in NewtodoActivity
        //Category Dropdown
        List<String> names= database.categoryDao().getAllTitels();
        String[] Categories = names.toArray(new String[0]);
        //Create an alert with multiple select Buttons
        textViewCat = findViewById(R.id.selectTVCategory);
        selectedKategory = new boolean[Categories.length];
        textViewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        EditTodoActivity.this
                );
                builder.setTitle("Kategorien Auswählen");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(Categories, selectedKategory, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            CategoryList.add(i);
                            Collections.sort(CategoryList);
                        } else {
                            CategoryList.remove(i);
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int j = 0; j < CategoryList.size(); j++) {
                            stringBuilder.append(Categories[CategoryList.get(j)]);
                            if (j != CategoryList.size() - 1) {
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
                        for (int j = 0; j < selectedKategory.length; j++) {
                            selectedKategory[j] = false;
                            CategoryList.clear();
                            textViewCat.setText("");
                        }
                    }
                });
                builder.show();
            }
        });
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //fetch the prioritys for the spinner
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

    //deletes the selected To-do
    public void delete(View view){
        database.todoDao().deleteById(TodoID);
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    //updates the current To-do
    public void update(View view){
        database = AppDatabase.getDatabase(getApplicationContext());
        EditText Titel = findViewById(R.id.TitelEdit);
        EditText Beschreibung = findViewById(R.id.descriptionEdit);
        EditText date = findViewById(R.id.DateEdit);
        Spinner spinner = (Spinner) findViewById(R.id.PriorityEdit);
        database.todoDao().deleteById(TodoID);  //usually the DB should detect that Updatestrategy = relace
        database.categoryTodoDao().deleteByID(TodoID);//in case it doesent the old to do gets deleted
        database.todoDao().addTodo(new Todo(Titel.getText().toString(),
                Beschreibung.getText().toString(), date.getText().toString(), spinner.getSelectedItemId() + 1));    //array starts by 0 but DB with 1 -> +1
        TextView cat = findViewById(R.id.selectTVCategory);
        String[] categories = cat.getText().toString().split(", ");
        for (int i = 0; i < categories.length; i++) {
            database.categoryTodoDao().addTodoCategory(new CategoryTodo(database.todoDao().getTodoByName(Titel.getText().toString()).get(0).getId(), database.categoryDao().getCategoryByName(categories[i]).get(0).getCategory_id()));
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        String currentDate= DateFormat.getDateInstance().format(calendar.getTime());
        EditText editText=findViewById(R.id.DateEdit);
        editText.setText(currentDate);
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
