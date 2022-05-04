package com.example.todo.Lists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View.OnClickListener;
import com.example.todo.AppDatabase;
import com.example.todo.Dao.PriorityDao;
import com.example.todo.Entity.Priority;
import com.example.todo.Entity.Todo;
import com.example.todo.MainActivity;
import com.example.todo.R;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<Todo> todos;
    MainActivity main;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Titel;
        public TextView Description;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            Titel = (TextView) v.findViewById(R.id.firstLine);
            Description = (TextView) v.findViewById(R.id.secondLine);
        }
    }

    public void add(int position, Todo item) {
        todos.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        todos.remove(position);
        notifyItemRemoved(position);
    }

    public TodoAdapter(List<Todo> myDataset) {
        todos = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v =
                inflater.inflate(R.layout.zeile, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Todo todo = todos.get(position);
        holder.Titel.setText(todo.getTitel());
        holder.Description.setText("Priorität: " +  todo.getPriorityId());
        holder.layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }
}