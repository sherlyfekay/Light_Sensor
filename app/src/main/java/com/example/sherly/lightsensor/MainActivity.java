package com.example.sherly.lightsensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //SensorManager lets you access the device's sensors
    //declare Variables
    RelativeLayout layoutSensor;
    private SensorManager sensorManager;
    TextView tvMaxValue, tvCurrentLight, judul;
    ImageView lamp;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutSensor = (RelativeLayout) findViewById(R.id.layoutSensor);
        tvMaxValue = (TextView)findViewById(R.id.tvMaxValue);
        tvCurrentLight = (TextView)findViewById(R.id.tvCurrentLight);
        judul = (TextView) findViewById(R.id.judul);
        lamp = (ImageView) findViewById(R.id.lamp);

        //create instance of sensor manager and get system service to interact with Sensor
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null){
            Toast.makeText(MainActivity.this,"No Light Sensor Found! ",Toast.LENGTH_LONG).show();
        }
        else {
            float max =  lightSensor.getMaximumRange();
            //Get Maximum Value From Light sensor
            tvMaxValue.setText("Max Range : " + String.valueOf(max));
            sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the lightSensor
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        if(sensorEvent.sensor.getType()==Sensor.TYPE_LIGHT){
            float currentLight = sensorEvent.values[0];
            if(currentLight<1){
                layoutSensor.setBackgroundColor(getResources().getColor(R.color.nolight));
                lamp.setImageDrawable(getResources().getDrawable(R.drawable.nolight));
                judul.setText("No Light");
                judul.setTextColor(getResources().getColor(R.color.white));
                tvMaxValue.setTextColor(getResources().getColor(R.color.white));
                tvCurrentLight.setTextColor(getResources().getColor(R.color.white));
                tvCurrentLight.setText("Current light : " + String.valueOf(currentLight));}
            else if(currentLight<5){
                layoutSensor.setBackgroundColor(getResources().getColor(R.color.dim));
                lamp.setImageDrawable(getResources().getDrawable(R.drawable.dim));
                judul.setTextColor(getResources().getColor(R.color.white));
                tvMaxValue.setTextColor(getResources().getColor(R.color.white));
                tvCurrentLight.setTextColor(getResources().getColor(R.color.white));
                judul.setText("Dim");
                tvCurrentLight.setText("Current light : " + String.valueOf(currentLight));}
            else if(currentLight<10){
                layoutSensor.setBackgroundColor(getResources().getColor(R.color.normal));
                lamp.setImageDrawable(getResources().getDrawable(R.drawable.normal));
                judul.setText("Normal");
                judul.setTextColor(getResources().getColor(R.color.black));
                tvMaxValue.setTextColor(getResources().getColor(R.color.black));
                tvCurrentLight.setTextColor(getResources().getColor(R.color.black));
                tvCurrentLight.setText("Current light : " + String.valueOf(currentLight));}
            else if(currentLight<100){
                layoutSensor.setBackgroundColor(getResources().getColor(R.color.bright));
                lamp.setImageDrawable(getResources().getDrawable(R.drawable.bright));
                judul.setText("Bright (Room)");
                judul.setTextColor(getResources().getColor(R.color.black));
                tvMaxValue.setTextColor(getResources().getColor(R.color.black));
                tvCurrentLight.setTextColor(getResources().getColor(R.color.black));
                tvCurrentLight.setText("Current light : " + String.valueOf(currentLight));}
            else {
                layoutSensor.setBackgroundColor(getResources().getColor(R.color.sun));
                lamp.setImageDrawable(getResources().getDrawable(R.drawable.sun));
                judul.setText("Bright (Sun)");
                judul.setTextColor(getResources().getColor(R.color.black));
                tvMaxValue.setTextColor(getResources().getColor(R.color.black));
                tvCurrentLight.setTextColor(getResources().getColor(R.color.black));
                tvCurrentLight.setText("Current light : " + String.valueOf(currentLight));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
