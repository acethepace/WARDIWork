package com.example.mallock.wardiapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FinalActivity extends AppCompatActivity implements SensorEventListener {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        textView = findViewById(R.id.textView);
        SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i != 3; i++) {
                sb.append(String.valueOf(event.values[i]));
                sb.append(", ");
            }

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            int score = 0;
            if (Math.abs(x - StaticClass.x) < Math.abs(x - StaticClass.x1))
                score -= 1;
            else
                score += 1;
            if (Math.abs(y - StaticClass.y) < Math.abs(y - StaticClass.y1))
                score -= 1;
            else
                score += 1;
            if (Math.abs(z - StaticClass.z) < Math.abs(z - StaticClass.z1))
                score -= 1;
            else
                score += 1;

            if (score < 0) {
                textView.setText("See, I told you");
            } else {
                textView.setText("Don’t you get bored of me");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
