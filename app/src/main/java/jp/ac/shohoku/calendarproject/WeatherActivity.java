package jp.ac.shohoku.calendarproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONException;

import java.io.IOException;

public class WeatherActivity extends Activity {

    private TextView weather;
    private TextView main;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        weather = (TextView) findViewById(R.id.weather);
        main = (TextView) findViewById(R.id.main);
        imageView = (ImageView) findViewById(R.id.image);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        Intent intent = getIntent();
        String cityName = intent.getStringExtra("cityName");

        new WeatherTask().execute(cityName);
    }

    public class WeatherTask extends AsyncTask<String, Void, WeatherInformation> {
        Exception exception;

        /**
         * 非同期処理実行前の処理（UI スレッドで実行）
         */
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE); //プログレスバーを見えるようにする
        }

        protected WeatherInformation doInBackground(String... params) {
            try {
                return WeatherApi.getWeather(params[0]); //アクセス処理は WeatherApi に任せる
            } catch (IOException | JSONException e) {
                exception = e;
            }
            return null;
        }

        protected void onPostExecute(WeatherInformation data) {
            super.onPostExecute(data);
            progressBar.setVisibility(View.GONE);

            if (data != null) {
                String weatherString = data.weather.main + ": " + data.weather.description;
                weather.setText(weatherString);
                String urlString = "http://api.openweathermap.org/img/w/" + data.weather.icon + ".png";
                ImageLoader task = new ImageLoader();
                task.execute(new ImageLoader.Request(imageView, urlString));
                String mainString = data.main.temp_c + "℃, " + data.main.humidity + "%, " + data.main.pressure + "hPa";
                main.setText(mainString); //result を書き換える
            } else if (exception != null) {
                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
