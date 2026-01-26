package com.example.first_app.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.first_app.EmotionLog;
import com.example.first_app.EmotionSummary;

import java.util.List;

@Dao
public interface EmotionDao {

    @Insert
    void insert(EmotionLog log);

    @Query("SELECT * FROM emotion_logs ORDER By timestamp DESC")
    List<EmotionLog> getAllLogs();

    @Query("SELECT emotion, COUNT(*) as count FROM emotion_logs WHERE timestamp >= :startTime GROUP BY emotion")
    List<EmotionSummary> getFilteredCounts(long startTime);
}
