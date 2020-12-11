package jp.ac.shohoku.calendarproject;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class YoteiActivity extends AppCompatActivity {

    private Button modoruButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shinki);

        modoruButton = findViewById(R.id.button3);
        modoruButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout yotei = findViewById(R.id.shinki);
                yotei.removeAllViews();
                getLayoutInflater().inflate(R.layout.activity_main, yotei);
            }
        });
    }
}
