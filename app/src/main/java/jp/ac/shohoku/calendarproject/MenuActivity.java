package jp.ac.shohoku.calendarproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {

    private Button modoru, todo, weather, diary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        // ボタンを押したときにイベントを取得できるようにする
        Button modoru = (Button) findViewById(R.id.button11);
        modoru.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //インテントに、この画面と、遷移する別の画面を指定する
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);

                //インテントで指定した別の画面に遷移する
                startActivity(intent);
            }
        });

        Button todo = (Button) findViewById(R.id.button6);
        todo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //インテントに、この画面と、遷移する別の画面を指定する
                Intent intent = new Intent(MenuActivity.this, TodoActivity.class);

                //インテントで指定した別の画面に遷移する
                startActivity(intent);
            }
        });

        Button diary = (Button) findViewById(R.id.button9);
        diary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //インテントに、この画面と、遷移する別の画面を指定する
                Intent intent = new Intent(MenuActivity.this, DiaryListActivity.class);

                //インテントで指定した別の画面に遷移する
                startActivity(intent);
            }
        });

    }
}