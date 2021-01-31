package jp.ac.shohoku.calendarproject;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageLoader extends AsyncTask<ImageLoader.Request, Void, ImageLoader.Result> {

    /**
     * Web サーバに要求するリクエストの内容を表すクラス
     */
    public static class Request{
        public final ImageView imageView; //天気のアイコンを貼り付ける ImageView
        public final String url; //画像の URL

        public Request(ImageView imageView, String url){
             this.imageView = imageView;
             this.url = url;
        }
    }

    /**
     * Web サーバからの結果を表すクラス
     */
    public static class Result{
        public final ImageView imageView;
        public final Bitmap bitmap;
        public final Exception exception;

        public Result(ImageView imageView, Bitmap bitmap){
            this.imageView = imageView;
            this.bitmap = bitmap;
            this.exception = null;
        }

        public Result(ImageView imageView, Exception exception){
            this.imageView = imageView;
            this.bitmap = null;
            this.exception = exception;
        }
    }

    @Override
    protected Result doInBackground(Request... params) {
         Request request = params[0];
         Result result = null;
         HttpURLConnection connection = null;

         try {
             URL url = new URL(request.url);
             connection = (HttpURLConnection) url.openConnection(); //HTTP のコネクションを開く
             Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream()); //ストリームから Bitmap
             result = new Result(request.imageView, bitmap);
         } catch (MalformedURLException e) {
             e.printStackTrace();
         } catch (IOException e) {
             result = new Result(request.imageView, e);
         } finally {
             if(connection != null) {
                 connection.disconnect(); //最後にはコネクションを切断
             }
         }
         return result;
    }

    protected void onPostExecute(Result result) { //バックグラウンド処理が終了した後に実行
        super.onPostExecute(result);

        if (result.bitmap != null) {
            result.imageView.setImageBitmap(result.bitmap); //imageView に取得した Bitmap を設定する
        }
    }
}