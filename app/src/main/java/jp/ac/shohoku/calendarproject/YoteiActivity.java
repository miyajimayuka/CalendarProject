package jp.ac.shohoku.calendarproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class YoteiActivity extends Activity {

    private Button modorubutton, decButton, showdate;
    private TextView nodate, datet;
    private EditText tit = null;
    private EditText memot = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shinki);

        // 戻るボタン処理
        // ボタンを押したときにイベントを取得できるようにする
        Button modorubutton = (Button) findViewById(R.id.button);
        modorubutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //インテントに、この画面と、遷移する別の画面を指定する
                Intent intent = new Intent(YoteiActivity.this, MainActivity.class);

                //インテントで指定した別の画面に遷移する
                startActivity(intent);
            }
        });

        // 決定ボタン処理
        EditText tit = (EditText)findViewById(R.id.titletext);
        TextView datet = (TextView)findViewById(R.id.nodate);
        EditText memot = (EditText)findViewById(R.id.memotext);
        Button decButton = (Button) findViewById(R.id.button3);
        decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YoteiActivity.this, MainActivity.class);
                //intent.putExtra("title", tit.getText().toString());
                //intent.putExtra("hiduke", datet.getText().toString());
                //intent.putExtra("memo", memot.getText().toString());
            }
        });



        // 日付選択
        showdate = (Button) findViewById(R.id.showdate);
        nodate = (TextView) findViewById(R.id.nodate);
        showdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // カレンダーインスタンスを取得
                Calendar c = Calendar.getInstance();

                // DatePickerDialogインスタンスを取得
                DatePickerDialog datePickerDialog = new DatePickerDialog(YoteiActivity.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // 設定した日付を取得する
                        nodate.setText(year + "." +
                                (month + 1) + "." + day + "");
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
                // ダイアログを表示する
                datePickerDialog.show();
            }
        });
    }
}
