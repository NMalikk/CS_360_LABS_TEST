package com.example.first_app;

import androidx.room.ColumnInfo;

public class EmotionSummary {
    @ColumnInfo(name = "emotion")
    public String emotionName;

    @ColumnInfo(name = "count")
    public int count;
}
