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

    private Button modorubutton, decButton, showdate, returnButton;
    private TextView nodate;
    private EditText dataTitle;
    private EditText memoText;
    private Intent intent;
    private String currentDate;

    private TextView notitle, emptydate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shinki);

        Intent intent = getIntent();

        findViews();

        init();

        // 戻るボタン処理
        // ボタンを押したときにイベントを取得できるようにする
        modorubutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //インテントに、この画面と、遷移する別の画面を指定する
                Intent intent = new Intent(YoteiActivity.this, MainActivity.class);

                //インテントで指定した別の画面に遷移する
                startActivity(intent);
            }
        });


        // 日付選択
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

        // 決定ボタン処理
        decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DBへ登録
                //saveList();
                //インテントに、この画面と、遷移する別の画面を指定する
                Intent intent = new Intent(YoteiActivity.this, MainActivity.class);
                intent.putExtra("DataTitle",dataTitle.getText().toString());
                intent.putExtra("MemoText",memoText.getText().toString());
                startActivity(intent);


            }
        });

        // 表示ボタン処理
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    /**
     * 各部品の結び付け
     */
    private void findViews() {
        dataTitle = (EditText) findViewById(R.id.titletext);
        memoText = (EditText) findViewById(R.id.memotext);

        nodate = (TextView) findViewById(R.id.nodate);

        // 日付選択ボタン
        showdate = (Button) findViewById(R.id.showdate);

        // 保存ボタン
        decButton = (Button) findViewById(R.id.button3);

        // 戻るボタン
        modorubutton = (Button) findViewById(R.id.button);

        // 表示ボタン
        returnButton = (Button) findViewById(R.id.button);

        // タイトル, 日付空のときの処理
        notitle = (TextView) findViewById(R.id.notitle);
        emptydate = (TextView) findViewById(R.id.emptydate);
    }

    /**
     * 初期値設定
     */
    private void init() {
        dataTitle.setText("");
        nodate.setText("");
        memoText.setText("");

        dataTitle.requestFocus();

        notitle.setText("");
        emptydate.setText("");
    }

    /**
     * EditText入力データDBへ登録
     */
    private void saveList() {
        String dateTitle = dataTitle.getText().toString();
        String dateText = nodate.getText().toString();
        String memotext = memoText.getText().toString();


        // DBへ登録
        DateBaseAdapter dateBaseAdapter = new DateBaseAdapter(this);
        dateBaseAdapter.openDB();
        dateBaseAdapter.saveDB(dateTitle, dateText, memotext);
        dateBaseAdapter.closeDB();

        init();

    }
}
