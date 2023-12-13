package com.example.weatherx

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ChooseCityActivity : AppCompatActivity() {

    private lateinit var etCity: EditText
    private lateinit var btnSelectCity: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_city)

        etCity = findViewById(R.id.etCity)
        btnSelectCity = findViewById(R.id.btnSelectCity)

        btnSelectCity.setOnClickListener {
            val selectedCity = etCity.text.toString().trim()

            if (selectedCity.isNotEmpty()) {
                // Zapisz wybrane miasto w SharedPreferences
                val sharedPreferences = getSharedPreferences("WeatherPreferences", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("selectedCity", selectedCity)
                editor.apply()

                // Przekazanie wybranego miasta do MainActivity2
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
                finish()
            } else {
                // Komunikat o błędzie, jeśli pole miasta jest puste
                Toast.makeText(this, "Enter a city name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
