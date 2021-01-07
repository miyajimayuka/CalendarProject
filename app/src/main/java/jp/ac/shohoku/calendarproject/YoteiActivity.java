package jp.ac.shohoku.calendarproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class YoteiActivity extends Activity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shinki);

        // ボタンを押したときにイベントを取得できるようにする
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //インテントに、この画面と、遷移する別の画面を指定する
                Intent intent = new Intent(YoteiActivity.this, MainActivity.class);

                //インテントで指定した別の画面に遷移する
                startActivity(intent);
            }
        });
    }
}