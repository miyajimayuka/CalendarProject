package jp.ac.shohoku.calendarproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DiaryAdapter {

    static final String DATABASE_NAME = "Diary.db";
    public static final String TABLE_NAME = "diary";
    static final int DATABASE_VERSION = 1;


    public static final String COL_ID = "_id";  // id
    public static final String COL_TITLE = "title";  // タイトル
    public static final String COL_UPDATE = "_update";  // 日付
    public static final String COL_TEXT = "text";  // テキスト

    protected Context context;
    protected DBHelper dbHelper;
    protected SQLiteDatabase db;

    public DiaryAdapter(Context context){
        this.context = context;
        dbHelper = new DBHelper(this.context);
    }

    /**
     * DBの読み書き
     */
    public DiaryAdapter openDB() {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    /**
     * DBの読み込み
     */
    //public DiaryAdapter readDB() {
        //db = dbHelper.getReadableDatabase();
        //return this;
    //}

    /**
     * DB閉じる
     */
    public void closeDB() {
        db.close();
        db = null;
    }

    /**
     * DBのレコード登録
     */
    public void saveDB(String title, String _update, String text) {

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();     // ContentValuesでデータを設定していく
            values.put(COL_TITLE, title);
            values.put(COL_UPDATE, _update);
            values.put(COL_TEXT, text);

            db.insert(TABLE_NAME, null, values);      // レコードへ登録

            db.setTransactionSuccessful();      // トランザクションへコミット
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();                // トランザクションの終了
        }
    }

    /**
     * DBのデータ取得
     */
    public Cursor getDB(String[] columns) {
        return db.query(TABLE_NAME, columns, null, null, null, null, null);
    }

    /**
     * DB検索データ取得
     */
    public Cursor searchDB(String[] columns, String column, String[] name) {
        return db.query(TABLE_NAME, columns, column + " like ?", name, null, null, null);
    }

    /**
     * DB全削除
     */
    public void allDelete() {

        db.beginTransaction();                      // トランザクション開始
        try {
            db.delete(TABLE_NAME, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();                    // トランザクションの終了
        }
    }

    /**
     * DBの単一削除
     */
    public void selectDelete(String position) {

        db.beginTransaction();                      // トランザクション開始
        try {
            db.delete(TABLE_NAME, COL_ID + "=?", new String[]{position});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();                    // トランザクションの終了
        }
    }

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "Diary.db", null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(
                    "CREATE TABLE " + TABLE_NAME +
                            " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + COL_TITLE + " TEXT NOT NULL ,"
                            + COL_UPDATE + " TEXT NOT NULL ,"
                            + COL_TEXT + " TEXT NOT NULL " + ");");
        }

        @Override
        public void onUpgrade(
                SQLiteDatabase db,
                int oldVersion,
                int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
