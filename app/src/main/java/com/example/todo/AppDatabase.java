package com.example.todo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.todo.Dao.CategoryDao;
import com.example.todo.Dao.PriorityDao;
import com.example.todo.Dao.TodoDao;
import com.example.todo.Entity.CategoryTodo;
import com.example.todo.Entity.Priority;
import com.example.todo.Entity.Todo;
import com.example.todo.Entity.Category;

@Database(entities = {Todo.class, Category.class, CategoryTodo.class, Priority.class
}, version = 16, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract TodoDao todoDao();
    public abstract CategoryDao categoryDao();
    public abstract PriorityDao priorityDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, AppDatabase.class, "Todos")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}