package com.example.todo.Lists;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Entity.Priority;
import com.example.todo.R;

import java.util.List;

public class PriorityAdapter extends RecyclerView.Adapter<PriorityAdapter.ViewHolder>{

    private List<Priority> priorities;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView prio;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            prio = (TextView) v.findViewById(R.id.PrioLine);
        }
    }


    public PriorityAdapter(List<Priority> priorities) {
        this.priorities = priorities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Priority priority=priorities.get(position);
        holder.prio.setText(priority.name);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}