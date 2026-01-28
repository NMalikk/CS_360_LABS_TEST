package com.example.first_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.first_app.db.AppDatabase;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getDatabase(this);
        int[] buttonIds = {R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6};

        for (int id : buttonIds) {
            Button btn = findViewById(id);
            if (btn != null){
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String emotion = ((Button) v).getText().toString();
                        handleButtonClick(emotion);
                    }
                });
            }

        }


        Button btnViewLogs = findViewById(R.id.btnViewLogs);
        //normal pres nagviates other page
        btnViewLogs.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
            startActivity(intent);
        });

        // long press calls the db sceeder
        btnViewLogs.setOnLongClickListener(v -> {
            seedDatabaseForDemo();
            return true;
        });


    }

    private void handleButtonClick(String emotion) {

        long timestamp = System.currentTimeMillis();
        EmotionLog newLog = new EmotionLog(emotion, timestamp);

        db.emotionDao().insert(newLog);

        Toast.makeText(this, "Logged: " + emotion, Toast.LENGTH_SHORT).show();
    }

    private void seedDatabaseForDemo() {
        AppDatabase db = AppDatabase.getDatabase(this);

        db.clearAllTables();

        long now = System.currentTimeMillis();
        long oneDay = 24L * 60 * 60 * 1000;

        //daa shosws in all time filter only
        db.emotionDao().insert(new EmotionLog("😢 Sad", now - (10 * oneDay)));

        //data shows in 7days filter
        db.emotionDao().insert(new EmotionLog("😡 Angry", now - oneDay));
        db.emotionDao().insert(new EmotionLog("😡 Angry", now - oneDay));


        Toast.makeText(this, "Database Reset & Seeded!", Toast.LENGTH_SHORT).show();
        //color = green
    }
}
