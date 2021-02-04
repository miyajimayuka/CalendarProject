package jp.ac.shohoku.calendarproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DateBaseAdapter {

    private final static String DB_NAME = "plans.db";      // DB名
    private final static String DB_TABLE = "mySheet";       // DBのテーブル名
    private final static int DB_VERSION = 1;                // DBのバージョン

    /**
     * DBのカラム名
     */
    public final static String COL_ID = "_id";             // id
    public final static String COL_DBTITLE = "dbtitle";    // タイトル
    public final static String COL_DBDATE = "dbdate";      // 日付
    public final static String COL_DBMEMO = "dbmemo";      // メモ

    private SQLiteDatabase db = null;           // SQLiteDatabase
    private DBHelper dbHelper = null;           // DBHepler
    protected Context context;                  // Context

    // コンストラクタ
    public DateBaseAdapter(Context context) {
        this.context = context;
        dbHelper = new DBHelper(this.context);
    }

    /**
     * DBの読み書き
     */
    public DateBaseAdapter openDB() {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    /**
     * DBを閉じる
     */
    public void closeDB() {
        db.close();     // DBを閉じる
        db = null;
    }

    /**
     * DBのレコード登録
     */
    public void saveDB(String dbtitle, String dbdate, String dbmemo) {

        db.beginTransaction();  // トランザクション開始

        try {
            ContentValues values = new ContentValues();     // ContentValuesでデータを設定していく
            values.put(COL_DBTITLE, dbtitle);
            values.put(COL_DBDATE, dbdate);
            values.put(COL_DBMEMO, dbmemo);

            db.insert(DB_TABLE, null, values);      // レコードへ登録

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
        return db.query(DB_TABLE, columns, null, null, null, null, null);
    }

    /**
     * DBの検索したデータ取得
     */
    public Cursor searchDB(String[] columns, String column, String[] name) {
        return db.query(DB_TABLE, columns, column + " like ?", name, null, null, null);
    }

    /**
     * DBのレコード全削除
     */
    public void allDelete() {

        db.beginTransaction();                      // トランザクション開始
        try {
            // deleteメソッド DBのレコードを削除
            // 第1引数：テーブル名
            // 第2引数：削除する条件式 nullの場合は全レコードを削除
            // 第3引数：第2引数で?を使用した場合に使用
            db.delete(DB_TABLE, null, null);        // DBのレコードを全削除
            db.setTransactionSuccessful();          // トランザクションへコミット
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();                    // トランザクションの終了
        }
    }

    /**
     * DBのレコード削除
     */
    public void selectDelete(String position) {

        db.beginTransaction();                      // トランザクション開始
        try {
            db.delete(DB_TABLE, COL_ID + "=?", new String[]{position});
            db.setTransactionSuccessful();          // トランザクションへコミット
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();                    // トランザクションの終了
        }
    }

    /**
     * DB生成やアップグレード管理
     */
    private static class DBHelper extends SQLiteOpenHelper {

        // コンストラクタ
        public DBHelper(Context context) {
            //第1引数：コンテキスト
            //第2引数：DB名
            //第3引数：factory nullでよい
            //第4引数：DBのバージョン
            super(context, "plans.db", null, 1);
        }

        /**
         * DB生成時に呼ばれる
         */
        @Override
        public void onCreate(SQLiteDatabase db) {

            //テーブルを作成するSQL文の定義 ※スペースに気を付ける
            String createTbl = "CREATE TABLE " + DB_TABLE + " ("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_DBTITLE + " dbtitle,"
                    + COL_DBDATE + " dbdate,"
                    + COL_DBMEMO + " dbmemo"
                    + ");";

            db.execSQL(createTbl);      //SQL文の実行
        }

        /**
         * DBアップグレード
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // DBからテーブル削除
            db.execSQL("DROP TABLE IF EXISTS" + DB_TABLE);
            // テーブル生成
            onCreate(db);
        }
    }
}
