package com.example.todo.Lists;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Activitys.MainActivity;
import com.example.todo.Entity.Todo;
import com.example.todo.R;
import com.example.todo.TodoDTO;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<TodoDTO> todos;
    private OnNoteListener mOnNoteListener;

    //defines the typ of the Adapter (TodoDTO)
    public TodoAdapter(List<TodoDTO> myDataset,OnNoteListener onNoteListener) {
        todos = myDataset;
        mOnNoteListener=onNoteListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public TextView Titel;
        public TextView Description;
        public View layout;
        OnNoteListener onNoteListener;

        //sets an onclickListener in the Layout
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

    public void add(int position, TodoDTO item) {
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

    //responsible for the Text in the RecyclerView also reads the size defined in settings and adjusts the Textsize according to it
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(holder.itemView.getContext());
        String size = preferences.getString("size", "");
            if (size.equals("mittel")){
                holder.Titel.setTextSize(15);
            }
            if (size.equals("groß")){
                holder.Titel.setTextSize(20);
            }
            if (size.equals("klein")){
                holder.Titel.setTextSize(10);
            }
        final TodoDTO todo = todos.get(position);
        holder.Titel.setText(todo.getTitel());
        holder.Description.setText("Priorität: " +  todo.getPrio());
    }

    //overwrite method's
    @Override
    public int getItemCount() {
        return todos.size();
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}