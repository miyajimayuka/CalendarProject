package jp.ac.shohoku.calendarproject;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;



public class MainActivity extends Activity {

    private TextView titleText;
    private Button prevButton, nextButton, yoteiButton, menuButton;
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
                //インテントに、この画面と、遷移する別の画面を指定する
                Intent intent = new Intent(MainActivity.this, YoteiActivity.class);

                //インテントで指定した別の画面に遷移する
                startActivity(intent);
            }
        });

        //メニューボタンを押したら追加画面へ
        menuButton = findViewById(R.id.button2);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //インテントに、この画面と、遷移する別の画面を指定する
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);

                //インテントで指定した別の画面に遷移する
                startActivity(intent);
            }
        });


    }

}