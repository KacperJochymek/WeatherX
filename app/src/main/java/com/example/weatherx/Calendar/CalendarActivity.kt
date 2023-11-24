package com.example.weatherx.Calendar

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherx.Compass.CompassActivity
import com.example.weatherx.FlashlightActivity
import com.example.weatherx.MainActivity2
import com.example.weatherx.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarActivity : AppCompatActivity() {

    private lateinit var tvDatePicker: TextView
    private lateinit var btnDatePicker: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_calendar)

        tvDatePicker = findViewById(R.id.tvDate)
        btnDatePicker = findViewById(R.id.btnDatePicker)

        val myCalendar = Calendar.getInstance()

        // Aktywacja, wybranej daty
        val datePicker =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel(myCalendar)
            }
        btnDatePicker.setOnClickListener {
            DatePickerDialog(
                this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(
                    Calendar.MONTH
                ),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity2::class.java))
                    true
                }
                R.id.calendar -> {
                    // Jesteś w sekcji Kalendarza
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
    }

    // Aktualizacja daty
    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        tvDatePicker.text = sdf.format(myCalendar.time)
    }
}
