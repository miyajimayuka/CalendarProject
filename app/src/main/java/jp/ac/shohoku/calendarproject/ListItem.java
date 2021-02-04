package jp.ac.shohoku.calendarproject;

import android.util.Log;

public class ListItem {

    protected int id;  // ID
    protected String title, update, text; // 日記タイトル, テキスト

    /**
     * ListItem
     */
    public ListItem(int id, String title, String update, String text) {
        this.id = id;
        this.title = title;
        this.update = update;
        this.text = text;
    }

    /**
     * タイトル取得
     */
    public String getTitle() {
        return title;
    }

    /**
     * 日付取得
     */
    public String getUpdate() {
        return update;
    }

    /**
     * テキスト取得
     */
    public String getText() {
        return text;
    }

    /**
     * id取得
     */
    public int getId() {
        Log.d("取得したID: ", String.valueOf(id));
        return id;
    }
}
