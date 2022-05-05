package com.example.todo.Activitys;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Lists.PriorityAdapter;
import com.example.todo.AppDatabase;
import com.example.todo.Entity.Priority;
import com.example.todo.R;

import java.util.List;

public class PriorityActivity extends AppCompatActivity implements PriorityAdapter.OnNoteListenerPrio{

    private AppDatabase database;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_prioritys);

//        List<Priority> prio = database.priorityDao().getAllPriority();
//        System.out.println(prio);
//        recyclerView = findViewById(R.id.recyclerView);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        mAdapter = new PriorityAdapter(prio,this);
//        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onNoteClick(int position) {

    }
}
