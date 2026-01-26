package com.example.first_app.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.first_app.EmotionLog;

@Database(entities = {EmotionLog.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EmotionDao emotionDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "emotilog_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
