package com.example.ritmocardiaco;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor heartRateSensor;
    private TextView heartRateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heartRateTextView = findViewById(R.id.heartRateTextView);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.BODY_SENSORS},1);
        }
        else{
            iniciarSensor();
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        detenerSensor();

    }

    private void iniciarSensor()
    {
        sensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void detenerSensor()
    {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_HEART_RATE)
        {
            float valor= event.values[0];
            heartRateTextView.setText("Ritmo cardíaco: " + valor);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
