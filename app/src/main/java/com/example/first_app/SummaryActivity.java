package com.example.first_app;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.first_app.db.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SummaryActivity extends AppCompatActivity {

    private TextView tvFrequency;
    private TextView tvLogs;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        tvFrequency = findViewById(R.id.tvFrequencyDisplay);
        tvLogs = findViewById(R.id.tvLogsDisplay);
        Spinner spinnerFilter = findViewById(R.id.spinnerFilter);
        db = AppDatabase.getDatabase(this);

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                long startTime = 0;
                long now = System.currentTimeMillis();

                if (position == 0) {
                    startTime = now - (24L * 60 * 60 * 1000);
                } else if (position == 1) {
                    startTime = now - (7L * 24 * 60 * 60 * 1000);
                } else {
                    startTime = 0;
                }
                updateData(startTime);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void updateData(long startTime) {
        List<EmotionSummary> summaryList = db.emotionDao().getFilteredCounts(startTime);
        String summaryText = "";
        int maxCount = 0;
        for (EmotionSummary item : summaryList) {
            if (item.count > maxCount) maxCount = item.count;
        }

        for (EmotionSummary item : summaryList) {
            int barLength = (maxCount > 0) ? (item.count * 15 / maxCount) : 0;
            String bar = "";
            for (int i = 0; i < barLength; i++) {
                bar += "■";
            }
            summaryText += item.emotionName + "\n" + bar + "  (" + item.count + ")\n\n";
        }
        tvFrequency.setText(summaryText.isEmpty() ? "No data yet." : summaryText);

        List<EmotionLog> allLogs = db.emotionDao().getAllLogs();
        String logText = "";
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, HH:mm:ss", Locale.getDefault());

        for (EmotionLog log : allLogs) {
            String time = sdf.format(new Date(log.timestamp));
            logText += "┃ " + time + "  →  " + log.emotion + "\n";
        }
        tvLogs.setText(logText.isEmpty() ? "No logs" : logText);
    }
}