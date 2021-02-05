package jp.ac.shohoku.calendarproject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private TextView titleText, datatitle, memoText;
    private Button prevButton, nextButton, yoteiButton, menuButton;
    private Adapter adapter;
    private GridView calendarGridView;

    private DateBaseAdapter dbAdapter;                // DBAdapter
    //private MyBaseAdapter bsAdapter;
    private List<DataItem> items;
    private DataItem dataItem;

    // 参照するDBのカラム：ID,タイトル, 日付, メモ
    private String[] columns = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DateBaseAdapterのコンストラクタ呼び出し
        dbAdapter = new DateBaseAdapter(this);

        // itemsのArrayListを生成
        items = new ArrayList<>();

        //bsAdapter = new MyBaseAdapter(this, items);

        findViews();

        Intent intent = getIntent();


        loadMydata();

        calendarGridView.setOnItemClickListener(this);

        // 前月へ
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.prevMonth();
                titleText.setText(adapter.getTitle());
            }
        });

        // 次月へ
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.nextMonth();
                titleText.setText(adapter.getTitle());
            }
        });
        adapter = new Adapter(this);
        calendarGridView.setAdapter(adapter);
        titleText.setText(adapter.getTitle());

        //予定追加ボタンを押したら追加画面へ
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

    /**
     * 各部品の結び付け
     */
    private void findViews() {

        titleText = findViewById(R.id.titleText);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        yoteiButton = findViewById(R.id.yoteiButton);
        menuButton = findViewById(R.id.button2);

        datatitle = findViewById(R.id.dataTitle);
        memoText = findViewById(R.id.memotext);

        calendarGridView = findViewById(R.id.calendarGridView);

    }

    /**
     * DBの読み込みと更新処理
     */
    private void loadMydata() {
        items.clear();

        dbAdapter.openDB();  // DBの読み込み

        // DBのデータ取得
        Cursor c = dbAdapter.getDB(columns);

        if (c.moveToFirst()) {
            do {
                // MyListItemのコンストラクタ呼び出し(myListItemのオブジェクト生成)
                dataItem = new DataItem(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3));

                Log.d("取得したCursor(ID):", String.valueOf(c.getInt(0)));
                Log.d("取得したCursor(タイトル):", c.getString(1));
                Log.d("取得したCursor(日付):", c.getString(2));
                Log.d("取得したCursor(メモ):", c.getString(3));

                items.add(dataItem);  // 取得した要素をitemsに追加

            } while (c.moveToNext());
        }
        c.close();
        dbAdapter.closeDB();
        //calendarGridView.setAdapter(bsAdapter);
        //bsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), YoteiActivity.class);
        intent.putExtra("date", adapter.getItem(position).toString());
        startActivity(intent);
    }


}