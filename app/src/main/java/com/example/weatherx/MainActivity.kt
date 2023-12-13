package com.example.weatherx

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin = findViewById(R.id.btnLogin)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username == "admin" && password == "admin") {
                // Poprawne logowanie
                val intent = Intent(this, ChooseCityActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Nieprawidłowe dane logowania
                Toast.makeText(this, "Nieprawidłowy login lub hasło", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

