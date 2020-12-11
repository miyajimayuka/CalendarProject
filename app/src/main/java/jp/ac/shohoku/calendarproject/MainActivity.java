package jp.ac.shohoku.calendarproject;


import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView titleText;
    private Button prevButton, nextButton, yoteiButton;
    private Adapter adapter;
    private GridView calendarGridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleText = findViewById(R.id.titleText);
        prevButton = findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.prevMonth();
                titleText.setText(adapter.getTitle());
            }
        });
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.nextMonth();
                titleText.setText(adapter.getTitle());
            }
        });
        calendarGridView = findViewById(R.id.calendarGridView);
        adapter = new Adapter(this);
        calendarGridView.setAdapter(adapter);
        titleText.setText(adapter.getTitle());

        //予定追加ボタンを押したら追加画面へ
        yoteiButton = findViewById(R.id.yoteiButton);
        yoteiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.linearlayout1);
                layout.removeAllViews();
                getLayoutInflater().inflate(R.layout.shinki, layout);
            }

        });
    }

}