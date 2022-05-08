package com.example.todo.Activitys;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.AppDatabase;
import com.example.todo.DatePickerFragment;
import com.example.todo.Entity.Priority;
import com.example.todo.Entity.Todo;
import com.example.todo.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditTodoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private AppDatabase database;
    private long TodoID;
    private RecyclerView.Adapter mAdapter;

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
        TodoID=id;
        database = AppDatabase.getDatabase(getApplicationContext());
        List<Todo> todo=database.todoDao().getTodoById(id);
        titel.setText(todo.get(0).Titel);
        desc.setText(todo.get(0).getBeschreibung());
        date.setText(todo.get(0).datetime);
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

    //todo solve Bug
    public void delete(View view){
        database.todoDao().deleteById(TodoID);
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    //todo update
    public void update(View view){
        EditText titeln = findViewById(R.id.TitelEdit);
        EditText descn = findViewById(R.id.descriptionEdit);
        EditText daten = findViewById(R.id.DateEdit);
        Spinner spinner= findViewById(R.id.PriorityEdit);
            List<Todo> todoo=database.todoDao().getTodoById(TodoID);
            todoo.get(0).setTitel(titeln.getText().toString());
            todoo.get(0).setBeschreibung(descn.getText().toString());
            todoo.get(0).setDatetime(daten.getText().toString());
            todoo.get(0).setPriorityId(spinner.getSelectedItemId());
        Intent intent=new Intent(this,MainActivity.class);
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
