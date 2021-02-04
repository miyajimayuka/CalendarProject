package jp.ac.shohoku.calendarproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class DiaryActivity extends Activity {

    private EditText editTitle, editdate, editText;  // タイトル, 日付, 日記
    private Button reButton, saveButton, readButton;  // 戻るボタン, 決定ボタン
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.niki);

        findViews();

        reButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //インテントに、この画面と、遷移する別の画面を指定する
                Intent intent = new Intent(DiaryActivity.this, DiaryListActivity.class);

                //インテントで指定した別の画面に遷移する
                startActivity(intent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveList();
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    intent = new Intent(DiaryActivity.this, DiaryListActivity.class);
                    startActivity(intent);
            }
        });
    }

    /**
     * 各部品の結び付け
     */
    private void findViews() {
        editTitle = (EditText) findViewById(R.id.editTitle);
        editdate = (EditText) findViewById(R.id.editdate);
        editText = (EditText) findViewById(R.id.editText);

        reButton = (Button) findViewById(R.id.reButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        readButton = (Button) findViewById(R.id.readButton);
    }

    /**
     * 初期値設定 (EditTextの入力欄は空白、※印は消す)
     * init()
     */
    private void init() {
        editTitle.setText("");
        editdate.setText("");
        editText.setText("");

        editTitle.requestFocus();      // フォーカスを品名のEditTextに指定
    }

    //データベースへ挿入するメソッド
    public void saveList() {

        // 各EditTextで入力されたテキストを取得
        String title = editTitle.getText().toString();
        String update = editdate.getText().toString();
        String text = editText.getText().toString();

        // DBへの登録処理
        DiaryAdapter diaryAdapter = new DiaryAdapter(this);
        diaryAdapter.openDB();                                         // DBの読み書き
        diaryAdapter.saveDB(title, update, text);   // DBに登録
        diaryAdapter.closeDB();                                        // DBを閉じる

        init();     // 初期値設定
    }

}