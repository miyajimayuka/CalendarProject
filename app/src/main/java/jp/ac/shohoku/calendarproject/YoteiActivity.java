package jp.ac.shohoku.calendarproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class YoteiActivity extends Activity {

    private Button modorubutton, showdate;
    private TextView nodate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shinki);

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

        showdate = (Button) findViewById(R.id.showdate);
        nodate = (TextView) findViewById(R.id.nodate);
        showdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                // カレンダーインスタンスを取得
                Calendar date = Calendar.getInstance();

                // DatePickerDialogインスタンスを取得
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        YoteiActivity.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(
                            DatePicker view,
                            int year,
                            int monthOfYear,
                            int dayOfMonth) {

                        // Setした日付を取得する
                        nodate.setText(year + "年" +
                                (monthOfYear + 1) + "月" + dayOfMonth + "日");

                    }
                }, date.get(Calendar.YEAR), date.get(Calendar.MONTH),
                        date.get(Calendar.DATE)
                );

                // ダイアログを表示する
                datePickerDialog.show();

            }
        });
    }
}
