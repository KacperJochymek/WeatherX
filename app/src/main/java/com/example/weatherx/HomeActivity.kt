package com.example.weatherx

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherx.Calendar.CalendarActivity
import com.example.weatherx.Compass.CompassActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottom_nav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.home->{
                startActivity(Intent(this,MainActivity2::class.java))
                return true
            }
            R.id.calendar->{
                startActivity(Intent(this, CalendarActivity::class.java))
                return true
            }
            R.id.compass->{
                startActivity(Intent(this,CompassActivity::class.java))
                return true
            }
            R.id.flashlight->{
                startActivity(Intent(this,FlashlightActivity::class.java))
                return true
            }
            else->false
        }
    }

}


