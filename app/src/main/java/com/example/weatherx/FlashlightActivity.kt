package com.example.weatherx

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherx.Calendar.CalendarActivity
import com.example.weatherx.Compass.CompassActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class FlashlightActivity : AppCompatActivity() {

    private lateinit var cameraM: CameraManager
    private lateinit var powerBtn: ImageButton
    private var isFlash = false

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_flashlight)

        powerBtn = findViewById(R.id.power)
        cameraM = getSystemService(CAMERA_SERVICE) as CameraManager
        powerBtn.setOnClickListener { flashLightOnOrOff(it) }

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

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
                    startActivity(Intent(this, CompassActivity::class.java))
                    true
                }
                R.id.flashlight -> {
                    // Jesteś w opcji Latarki
                    true
                }
                else -> false
            }
        }
    }

    // Metoda do włączania/wyłączania latarki
    @RequiresApi(Build.VERSION_CODES.M)
    private fun flashLightOnOrOff(v: View?) {

        if (!isFlash) {
            val cameraListId = cameraM.cameraIdList[0]
            cameraM.setTorchMode(cameraListId, true)
            isFlash = true
            powerBtn.setImageResource(R.drawable.ic_power_on)
            textMassge("Latarka włączona", this)
        } else {
            val cameraListId = cameraM.cameraIdList[0]
            cameraM.setTorchMode(cameraListId, false)
            isFlash = false
            powerBtn.setImageResource(R.drawable.ic_power_off)
            textMassge("Latarka wyłączona", this)
        }
    }

    private fun textMassge(s: String, c: Context) {
        Toast.makeText(c, s, Toast.LENGTH_SHORT).show()
    }
}
