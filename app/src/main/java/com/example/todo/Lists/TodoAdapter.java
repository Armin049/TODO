package com.example.todo.Lists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Entity.Todo;
import com.example.todo.R;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<Todo> todos;
    private OnNoteListener mOnNoteListener;

    public TodoAdapter(List<Todo> myDataset,OnNoteListener onNoteListener) {
        todos = myDataset;
        mOnNoteListener=onNoteListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public TextView Titel;
        public TextView Description;
        public View layout;
        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View v, OnNoteListener onNoteListener) {
            super(v);
            layout = v;
            Titel = (TextView) v.findViewById(R.id.firstLine);
            Description = (TextView) v.findViewById(R.id.secondLine);
            this.onNoteListener =onNoteListener;

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v =
                inflater.inflate(R.layout.zeile, parent, false);
        ViewHolder vh = new ViewHolder(v,mOnNoteListener);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Todo todo = todos.get(position);
        holder.Titel.setText(todo.getTitel());
        holder.Description.setText("Priorität: " +  todo.getPriorityId());
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }


    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}