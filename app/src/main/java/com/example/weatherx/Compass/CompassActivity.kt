package com.example.weatherx.Compass

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherx.CalendarActivity
import com.example.weatherx.FlashlightActivity
import com.example.weatherx.MainActivity2
import com.example.weatherx.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class CompassActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var magnetometer: Sensor? = null
    private var accelerometer: Sensor? = null
    private lateinit var compass: ImageView
    private lateinit var bottomNavigationView: BottomNavigationView

    private val ALPHA = 0.97f
    private var azimuth: Float = 0.0f
    private val gravity = FloatArray(3)
    private val geomagnetic = FloatArray(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.compass_view)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (magnetometer == null || accelerometer == null) {
            Toast.makeText(this, "Brak odpowiednich sensorów na tym urządzeniu.", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL)
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }

        compass = findViewById(R.id.compass)

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
                    true
                }
                R.id.flashlight -> {
                    startActivity(Intent(this, FlashlightActivity::class.java))
                    true
                }
                else -> false
            }
        }

        bottomNavigationView.menu.findItem(R.id.compass).isChecked = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Obsługa innych elementów menu
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, gravity, 0, 3)
        } else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, geomagnetic, 0, 3)
        }

        val rotationMatrix = FloatArray(9)
        val success = SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geomagnetic)

        if (success) {
            val orientationValues = FloatArray(3)
            SensorManager.getOrientation(rotationMatrix, orientationValues)

            azimuth = orientationValues[0]
            azimuth = Math.toDegrees(azimuth.toDouble()).toFloat()
            azimuth = (azimuth + 360) % 360

            compass.rotation = -azimuth
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Dokładność sensora
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
