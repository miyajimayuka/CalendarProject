package jp.ac.shohoku.calendarproject;

import android.app.Activity;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView titleText;
    private Button menuButton;
    private DateMonth mDateMonth;
    private GridView calendarGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleText = findViewById(R.id.titleText);
        menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDateMonth.nextMonth();
                titleText.setText(mDateMonth.getTitle());
            }
        });
        calendarGridView = findViewById(R.id.calendarGridView);
        mDateMonth = new DateMonth(this);
        calendarGridView.setAdapter(mDateMonth);
        titleText.setText(mDateMonth.getTitle());
    }

}