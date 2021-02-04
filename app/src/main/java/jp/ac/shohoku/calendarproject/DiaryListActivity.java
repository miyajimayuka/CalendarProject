package jp.ac.shohoku.calendarproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class DiaryListActivity extends Activity {

    private DiaryAdapter diaryAdapter;
    private BSAdapter baseAdapter;
    private List<ListItem> items;
    private ListView listView;
    static ListItem listItem;

    private Button  addButton, returnButton;

    // カラム名:id, タイトル, 日付, テキスト
    private String[] columns = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_list);

        diaryAdapter = new DiaryAdapter(this);
        items = new ArrayList<>();

        baseAdapter = new BSAdapter(this, items);

        findViews();

        loadData(); // DB読み込みと更新

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                // アラートダイアログ表示
                AlertDialog.Builder builder = new AlertDialog.Builder(DiaryListActivity.this);
                builder.setTitle("削除");
                builder.setMessage("削除しますか？");

                // OKの時の処理
                builder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // idの取得
                        listItem = items.get(position);
                        int listId = listItem.getId();

                        diaryAdapter.openDB();     // DBの読み込み(読み書きの方)
                        diaryAdapter.selectDelete(String.valueOf(listId));     // DBから取得したIDが入っているデータを削除する
                        Log.d("Long click : ", String.valueOf(listId));
                        diaryAdapter.closeDB();    // DBを閉じる
                        loadData();
                    }
                });

                builder.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                // ダイアログの表示
                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
            }
        });

        // 日記追加ボタン
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //インテントに、この画面と、遷移する別の画面を指定する
                Intent intent = new Intent(DiaryListActivity.this, DiaryActivity.class);

                //インテントで指定した別の画面に遷移する
                startActivity(intent);
            }
        });

        // メニュー画面に戻るボタン
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //インテントに、この画面と、遷移する別の画面を指定する
                Intent intent = new Intent(DiaryListActivity.this, MenuActivity.class);

                //インテントで指定した別の画面に遷移する
                startActivity(intent);
            }
        });
    }

    protected void findViews(){
        listView = (ListView)findViewById(R.id.listView);
        addButton = (Button)findViewById(R.id.addButton);
        returnButton = (Button)findViewById(R.id.returnButton);
    }

    /**
     * DBの読み込みと更新
     */

    protected void loadData(){

        items.clear();

        // DBの読み込み
        diaryAdapter.openDB();

        // DBのデータ取得
        Cursor c = diaryAdapter.getDB(columns);

        if(c.moveToFirst()){
            do {
                listItem = new ListItem(
                        c.getInt(c.getColumnIndex(DiaryAdapter.COL_ID)),
                        c.getString(c.getColumnIndex(DiaryAdapter.COL_TITLE)),
                        c.getString(c.getColumnIndex(DiaryAdapter.COL_UPDATE)),
                        c.getString(c.getColumnIndex(DiaryAdapter.COL_TEXT)));

                Log.d("取得したCursor(ID):", String.valueOf(c.getInt(0)));
                Log.d("取得したCursor(タイトル):", c.getString(1));
                Log.d("取得したCursor(日付):", c.getString(2));
                Log.d("取得したCursor(テキスト):", c.getString(3));

                items.add(listItem);

            } while(c.moveToNext());
        }
        c.close();
        diaryAdapter.closeDB();
        listView.setAdapter(baseAdapter);
        baseAdapter.notifyDataSetChanged();
    }

    /**
     * BaseAdapter継承クラス
     */
    public class BSAdapter extends BaseAdapter {

        private Context context;
        private List<ListItem> items;

        // 毎回findViewByIdをする事なく、高速化が出来るようするholderクラス
        private class ViewHolder {
            TextView textTitle;
            TextView textupdate;
            TextView textText;
        }

        // コンストラクタの生成
        public BSAdapter(Context context, List<ListItem> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // 新しいデータが表示されるタイミングで呼び出される
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            ViewHolder holder;

            // データ取得
            listItem = items.get(position);

            if (v == null) {
                LayoutInflater inflater =
                        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.row_select_listview, null);

                TextView titleText = (TextView) v.findViewById(R.id.title);
                TextView updateText = (TextView) v.findViewById(
                        R.id.update);
                TextView textView = (TextView) v.findViewById(
                        R.id.text);

                // holderにviewを持たせておく
                holder = new ViewHolder();
                holder.textTitle = titleText;
                holder.textupdate = updateText;
                holder.textText = textView;
                v.setTag(holder);

            } else {
                // 初めて表示されるときにつけておいたtagを元にviewを取得する
                holder = (ViewHolder) v.getTag();
            }

            // 取得した各データを各TextViewにセット
            holder.textTitle.setText(listItem.getTitle());
            holder.textupdate.setText(listItem.getUpdate());
            holder.textText.setText(listItem.getText());

            return v;

        }
    }
}