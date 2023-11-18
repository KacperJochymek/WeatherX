package com.example.weatherx.Compass

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherx.Calendar.CalendarActivity
import com.example.weatherx.FlashlightActivity
import com.example.weatherx.MainActivity2
import com.example.weatherx.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class CompassActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var magnetometer: Sensor? = null
    private lateinit var compass: ImageView
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.compass_view)

        // Inicjalizacja sensorów
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        compass = findViewById(R.id.compass)

        if (magnetometer == null) {
            // Obsługa braku dostępu do magnetometru
            // Dodaj odpowiednie działania w przypadku braku magnetometru
        }

        // Rozpoczęcie nasłuchiwania na zmiany kierunku
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL)

        // Inicjalizacja BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity2::class.java))
                    true
                }
                R.id.calendar -> {
                    startActivity(Intent(this, CalendarActivity::class.java))
                    true
                }
                R.id.compass -> {
                    // Nie rób nic, już jesteś w CompassActivity
                    true
                }
                R.id.flashlight -> {
                    startActivity(Intent(this, FlashlightActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Obsługa kliknięcia w element menu (jeśli wymagane)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor == magnetometer) {
            val azimuth = Math.toDegrees(event.values[0].toDouble()).toFloat()
            val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotation_animation)

            // Poprawka: Ustawianie animacji i rotacji na ImageView
            compass.startAnimation(rotateAnimation)
            rotateAnimation.fillAfter = true
            compass.rotation = -azimuth
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Nie jesteśmy zainteresowani dokładnością w tym przypadku
    }

    override fun onResume() {
        super.onResume()
        // Wznawianie nasłuchiwania sensora po wznowieniu aktywności
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        // Zatrzymywanie nasłuchiwania sensora po zatrzymaniu aktywności
        sensorManager.unregisterListener(this)
    }
}

