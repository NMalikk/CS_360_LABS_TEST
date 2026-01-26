package com.example.first_app;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "emotion_logs")
public class EmotionLog {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String emotion;

    public long timestamp;

    public EmotionLog(String emotion, long timestamp) {
        this.emotion = emotion;
        this.timestamp = timestamp;
        //comment added.
    }
}
