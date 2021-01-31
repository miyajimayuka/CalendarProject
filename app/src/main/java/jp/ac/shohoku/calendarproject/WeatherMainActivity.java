package jp.ac.shohoku.calendarproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class WeatherMainActivity extends Activity implements RadioGroup.OnCheckedChangeListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_weather_main);

         // ボタンを押したときにイベントを取得できるようにする
         Button selectedButton = (Button) findViewById(R.id.selectButton);//selectButton
         // 都市のグループをラジオボタンで表現
         final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.cityGroup); //書き換えないので final

         radioGroup.setOnCheckedChangeListener(this); //ラジオボタンのチェックに反応するように設定

         selectedButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             //インテントに、この画面と、遷移する別の画面を指定する
             RadioButton radioButton = (RadioButton)radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
             String cityName = radioButton.getText().toString();
             Intent intent = new Intent(WeatherMainActivity.this, WeatherActivity.class);
             intent.putExtra("cityName", cityName);
             //インテントで指定した別の画面に遷移する
             startActivity(intent);
         }
         });
    }

    //ラジオボタンがチェックされた時の処理（実際にはアプリ内でこの処理は使っていない）
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        // どれも選択されていなければ id には-1 が入ってくる
        if (-1 == checkedId) {
             Toast.makeText(this, "selection was cleared!", Toast.LENGTH_SHORT).show();
        } else {
            RadioButton radioButton = (RadioButton)radioGroup.findViewById(checkedId);//Id からラジオボタン
            String cityName = radioButton.getText().toString(); //ラジオボタンに設定されているテキスト
        }
    }
}
