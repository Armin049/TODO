package com.example.todo.Lists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Entity.Category;
import com.example.todo.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    private List<Category> categorys;
    private OnNoteListenerCat mOnNoteListenerCat;

    public CategoryAdapter(List<Category> myDataset, OnNoteListenerCat onNoteListenerCat) {
        categorys = myDataset;
        mOnNoteListenerCat = onNoteListenerCat;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView Titel;
        public Button button;
        public View layout;
        OnNoteListenerCat onNoteListenerCat;

        public ViewHolder(@NonNull View v, OnNoteListenerCat onNoteListenerCat) {
            super(v);
            layout = v;
            Titel = (TextView) v.findViewById(R.id.PrioLine);
            button = (Button) v.findViewById(R.id.buttonDelete);
            this.onNoteListenerCat = onNoteListenerCat;

            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListenerCat.onNoteClick(getAdapterPosition());
        }
    }

    public void add(int position, Category item) {
        categorys.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        categorys.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v =
                inflater.inflate(R.layout.display_prio, parent, false);
        CategoryAdapter.ViewHolder vh = new CategoryAdapter.ViewHolder(v, mOnNoteListenerCat);
        return vh;
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, final int position) {
        final Category Category = categorys.get(position);
        holder.Titel.setText(Category.getName());
    }

    @Override
    public int getItemCount() {
        return categorys.size();
    }


    public interface OnNoteListenerCat {
        void onNoteClick(int position);
    }
}
