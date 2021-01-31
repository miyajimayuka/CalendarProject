package jp.ac.shohoku.calendarproject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApi {

    private static final String HTTP_PREPOS =
            "http://api.openweathermap.org/data/2.5/weather?APPID=45563adc11a81a76e747f5d36813fa63&q=";

    /**
     * 天気情報を取得する
     * @param cityName 都市名
     * @return 都市の天気情報
     * @throws IOException HTTP アクセスによる入力の例外
     */

     public static WeatherInformation getWeather(String cityName) throws IOException, JSONException {
         cityName = cityName.replace(" ", "%20");
         URL uri = new URL(HTTP_PREPOS + cityName); //URL を作成
         HttpURLConnection connection = (HttpURLConnection) uri.openConnection(); //HTTP コネクションを開く
         StringBuilder sb = new StringBuilder(); //受け取った文字列を組み立てる

         try{
             BufferedReader br = new BufferedReader(
                     new InputStreamReader(connection.getInputStream())); //入力ストリームを作る
             String line;
             while((line = br.readLine()) != null) { //入力ストリームから一行ずつ呼み込んで
                 sb.append(line); //追加していく
             }
         } finally {
             connection.disconnect(); //すべて終了したら切断
         }
         return new WeatherInformation(new JSONObject(sb.toString())); //組み立てたデータを文字列にして返す
     }

}
