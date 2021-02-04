package jp.ac.shohoku.calendarproject;

import android.util.Log;

public class DataItem {

    protected int id;           // ID
    protected String title;   // タイトル
    protected String date;    // 日付
    protected String memo;    // メモ

    /**
     * DataItem()
     */
    public DataItem(int id, String title, String date, String memo) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.memo = memo;
    }

    /**
     * ID取得
     */
    public int getId() {
        Log.d("取得したID：", String.valueOf(id));
        return id;
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
    public String getDate() {
        return date;
    }

    /**
     * メモ取得
     */
    public String getMemo() {
        return memo;
    }
}
