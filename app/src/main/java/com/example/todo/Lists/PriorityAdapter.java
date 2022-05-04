package com.example.todo.Lists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Entity.Priority;
import com.example.todo.R;

import java.util.List;

public class PriorityAdapter extends RecyclerView.Adapter<PriorityAdapter.ViewHolder>{

    private List<Priority> priorities;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView prio;
        public View layout;
        public Button delete;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            prio = (TextView) v.findViewById(R.id.PrioLine);
            delete = v.findViewById(R.id.buttonDelete);
        }
    }

    public void add(int position, Priority item) {
        priorities.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        priorities.remove(position);
        notifyItemRemoved(position);
    }

    public PriorityAdapter(List<Priority> priorities) {
        this.priorities = priorities;
    }

    @Override
    public PriorityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v =
                inflater.inflate(R.layout.display_prio, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        final Priority priority=priorities.get(position);
        holder.prio.setText(priority.getName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}