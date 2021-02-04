package jp.ac.shohoku.calendarproject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private TextView titleText;
    private Button prevButton, nextButton, yoteiButton, menuButton;
    private Adapter adapter;
    private GridView calendarGridView;

    private DateBaseAdapter dbAdapter;                // DBAdapter
    private MyBaseAdapter myBaseAdapter;
    private List<DataItem> items;
    private DataItem dataItem;

    // 参照するDBのカラム：ID,品名,産地,個数,単価の全部なのでnullを指定
    private String[] columns = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        // DateBaseAdapterのコンストラクタ呼び出し
        dbAdapter = new DateBaseAdapter(this);

        // itemsのArrayListを生成
        items = new ArrayList<>();

        // MyBaseAdapterのコンストラクタ呼び出し(myBaseAdapterのオブジェクト生成)
        myBaseAdapter = new MyBaseAdapter(this, items);


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

        calendarGridView = findViewById(R.id.calendarGridView);
    }

    /**
     * DBの読み込みと更新処理
     */
    private void loadList() {
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
        calendarGridView.setAdapter(myBaseAdapter);
        myBaseAdapter.notifyDataSetChanged();
    }

    /**
     * BaseAdapterを継承したクラス
     */
    public class MyBaseAdapter extends BaseAdapter {

        private Context context;
        private List<DataItem> items;

        // 高速化できるようにholderクラス
        private class ViewHolder {
            TextView dataTitle;
            TextView textdate;
            TextView memotext;
        }

        // コンストラクタの生成
        public MyBaseAdapter(Context context, List<DataItem> items) {
            this.context = context;
            this.items = items;
        }

        // Listの要素数を返す
        @Override
        public int getCount() {
            return items.size();
        }

        // indexやオブジェクトを返す
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        // IDを他のindexに返す
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 新しいデータが表示されるタイミングで呼び出される
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = convertView;
            ViewHolder holder;

            // データを取得
            dataItem = items.get(position);


            if (view == null) {
                LayoutInflater inflater =
                        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.cal_cell, parent, false);

                TextView dataTitle = (TextView) view.findViewById(R.id.dataTitle);      // 品名のTextView
                TextView textdate = (TextView) view.findViewById(R.id.textdate);        // 産地のTextView
                TextView memotext = (TextView) view.findViewById(R.id.memotext);        // 個数のTextView

                // holderにviewを持たせておく
                holder = new ViewHolder();
                holder.dataTitle = dataTitle;
                holder.textdate = textdate;
                holder.memotext = memotext;
                view.setTag(holder);

            } else {
                // 初めて表示されるときにつけておいたtagを元にviewを取得する
                holder = (ViewHolder) view.getTag();
            }

            // 取得した各データを各TextViewにセット
            holder.dataTitle.setText(dataItem.getTitle());
            holder.textdate.setText(dataItem.getDate());
            holder.memotext.setText(dataItem.getMemo());

            return view;


        }
    }
}