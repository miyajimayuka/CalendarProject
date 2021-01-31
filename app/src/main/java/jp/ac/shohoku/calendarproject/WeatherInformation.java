package jp.ac.shohoku.calendarproject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherInformation {

    public final Coordinate coord;
    public final Weather weather;
    public final Main main;

    public WeatherInformation(JSONObject jsonObject) throws JSONException {
         JSONObject coordObject = jsonObject.getJSONObject("coord");
         coord = new Coordinate(coordObject);
         JSONArray weatherArray = jsonObject.getJSONArray("weather");
         weather = new Weather(weatherArray.getJSONObject(0));
         JSONObject mainObject = jsonObject.getJSONObject("main");
         main = new Main(mainObject);
    }

    /**
     * 経度と緯度のクラス
     */
    public static class Coordinate {
         public final String longitude;
         public final String latitude;

         public Coordinate(JSONObject jsonObject) throws JSONException {
             longitude = jsonObject.getString("lon");
             latitude = jsonObject.getString("lat");
         }
    }

    /**
     * 天気情報のクラス
     */
     public static class Weather {
         public final String main;
         public final String description;
         public final String icon;

         public Weather(JSONObject jsonObject) throws JSONException {
             main = jsonObject.getString("main");
             description = jsonObject.getString("description");
             icon = jsonObject.getString("icon");
         }
     }

     /**
      * 気温や湿度などのクラス
      */
     public static class Main{
         public final String temp;
         public final String temp_c; //摂氏の温度
         public final String pressure;
         public final String humidity;
         public final String temp_min;
         public final String temp_max;

         public Main(JSONObject jsonObject) throws JSONException {
             temp = jsonObject.getString("temp"); //ケルビンの温度（絶対温度）
             pressure = jsonObject.getString("pressure");
             humidity = jsonObject.getString("humidity");
             temp_min = jsonObject.getString("temp_min");
             temp_max = jsonObject.getString("temp_max");
             float tmp = (new Float(temp)).floatValue() - 273.15F; //0℃は 273.15K
             temp_c = String.format("%.1f", tmp);
         }
     }

     /**
      * 風のクラス．風速と向きを表すクラス
      */
     public static class Wind{
         public final String speed;
         public final String direction;

         public Wind(JSONObject jsonObject)throws JSONException {
             speed = jsonObject.getString("speed");
             direction = jsonObject.getString("direction");
         }
     }
}