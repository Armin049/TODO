package com.example.todo.Lists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Entity.Priority;
import com.example.todo.R;

import java.util.List;

public class PriorityAdapter extends RecyclerView.Adapter<PriorityAdapter.ViewHolder> {
    private List<Priority> prioritys;
    private OnNoteListenerPrio mOnNoteListenerPrio;


    public PriorityAdapter(List<Priority> myDataset, OnNoteListenerPrio onNoteListenerPrio) {
        prioritys = myDataset;
        mOnNoteListenerPrio = onNoteListenerPrio;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView Titel;
        public TextView Description;
        public View layout;
        OnNoteListenerPrio onNoteListenerPrio;

        public ViewHolder(@NonNull View v, OnNoteListenerPrio onNoteListenerPrio) {
            super(v);
            layout = v;
            Titel = (TextView) v.findViewById(R.id.firstLine);
            Description = (TextView) v.findViewById(R.id.secondLine);
            this.onNoteListenerPrio = onNoteListenerPrio;

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListenerPrio.onNoteClick(getAdapterPosition());
        }
    }

    public void add(int position, Priority item) {
        prioritys.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        prioritys.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v =
                inflater.inflate(R.layout.zeile, parent, false);
        ViewHolder vh = new ViewHolder(v, mOnNoteListenerPrio);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Priority Priority = prioritys.get(position);

    }

    @Override
    public int getItemCount() {
        return prioritys.size();
    }


    public interface OnNoteListenerPrio {
        void onNoteClick(int position);
    }
}