package com.example.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Entity.Todo;

import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {
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

    // Provide a suitable constructor (depends on the kind of dataset)
    public myAdapter(List<Todo> myDataset) {
        todos = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.zeile, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Todo name = todos.get(position);
        holder.Titel.setText(name.getTitel());
        holder.Description.setText("Priorität: " + name.getBeschreibung());
        holder.Titel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Adapter: "+ todos.get(position).getId());
                  main.edit(todos.get(position).getId());
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return todos.size();
    }
}