package com.example.mallock.wardiapp;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CallibrationActivity1 extends AppCompatActivity implements SensorEventListener {

    TextView textView;
    Button button;
    ArrayList<Float> xValues = new ArrayList<>();
    ArrayList<Float> yValues = new ArrayList<>();
    ArrayList<Float> zValues = new ArrayList<>();

    boolean callibrating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setEnabled(false);
                button.setText("Callibrating...");
                callibrating = true;
                Handler h = new Handler();
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        callibrating = false;
                        float sumX = 0;
                        for (float x : xValues)
                            sumX += x;
                        float averageX = sumX / xValues.size();

                        float sumY = 0;
                        for (float y : yValues)
                            sumY += y;
                        float averageY = sumY / yValues.size();

                        float sumZ = 0;
                        for (float z : zValues)
                            sumZ += z;
                        float averageZ = sumZ / zValues.size();

                        StaticClass.x = averageX;
                        StaticClass.y = averageY;
                        StaticClass.z = averageZ;
                        button.setEnabled(true);
                        String text = averageX + "," + averageY + ", " + averageZ;
                        textView.setText(text);
                        startActivity(new Intent(CallibrationActivity1.this, CallibrationActivity2.class));
                    }
                };
                h.postDelayed(r, 5000);
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR && callibrating) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i != 3; i++) {
                sb.append(String.valueOf(event.values[i]));
                sb.append(", ");
            }
            xValues.add(event.values[0]);
            yValues.add(event.values[1]);
            zValues.add(event.values[2]);

            textView.setText(sb.toString());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
