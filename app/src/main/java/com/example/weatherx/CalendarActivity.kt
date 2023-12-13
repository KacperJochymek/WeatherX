package com.example.weatherx

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherx.Compass.CompassActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class CalendarActivity : AppCompatActivity(), SensorEventListener {


    private var sensorManager: SensorManager? = null
    private var running = false
    private var lastSteps = 0f
    private var totalSteps = 0f
    private var lastResetTime = System.currentTimeMillis()

    val ACTIVITY_RECOGNITION_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_calendar)

        if (isPermissionGranted()) {
            requestPermission()
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity2::class.java))
                    true
                }
                R.id.calendar -> {
                    true
                }
                R.id.compass -> {
                    startActivity(Intent(this, CompassActivity::class.java))
                    true
                }
                R.id.flashlight -> {
                    startActivity(Intent(this, FlashlightActivity::class.java))
                    true
                }
                else -> false
            }
        }

        bottomNavigationView.menu.findItem(R.id.calendar).isChecked = true

        var tv_stepsTaken = findViewById<TextView>(R.id.tv_stepsTaken)

        tv_stepsTaken.setOnLongClickListener {
            resetStepCounter()
            true
        }
    }

    //Sprawdzenie czy na urządzeniu jest sensor + usuwanie listenera
    override fun onResume() {
        super.onResume()
        running = true

        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            Toast.makeText(this, "Brak sensora, na tym urządzeniu.", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.unregisterListener(this) // Usuwamy poprzedniego listenera
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        var tv_stepsTaken = findViewById<TextView>(R.id.tv_stepsTaken)

        if (running) {
            totalSteps = event!!.values[0]

            val currentSteps = (totalSteps - lastSteps).toInt()

            tv_stepsTaken.text = ("$currentSteps")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Ignore
    }

    private fun resetStepCounter() {
        lastResetTime = System.currentTimeMillis()
        lastSteps = totalSteps
        var tv_stepsTaken = findViewById<TextView>(R.id.tv_stepsTaken)
        tv_stepsTaken.text = "0"
        Toast.makeText(this, "Pomyślnie zresetowano licznik", Toast.LENGTH_SHORT).show()
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                ACTIVITY_RECOGNITION_REQUEST_CODE
            )
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACTIVITY_RECOGNITION
        ) != PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ACTIVITY_RECOGNITION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // do nothing
                }
            }
        }
    }
}