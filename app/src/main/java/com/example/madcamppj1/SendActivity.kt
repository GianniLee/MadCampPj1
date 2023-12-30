package com.example.madcamppj1

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class SendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.selectedItemId = R.id.bottom_send
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.bottom_person -> {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.bottom_image -> {
                    startActivity(Intent(applicationContext, GalleryActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.bottom_send -> return@setOnItemSelectedListener true
            }
            false
        }
    }
}