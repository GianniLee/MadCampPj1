package com.example.madcamppj1

import android.content.Intent
import android.os.Bundle
//import android.support.v4.app.DialogFragment
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.selectedItemId = R.id.bottom_person
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.bottom_person -> return@setOnItemSelectedListener true
                R.id.bottom_image -> {
                    startActivity(Intent(applicationContext, GalleryActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.bottom_send -> {
                    startActivity(Intent(applicationContext, SendActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

        val listView: ListView = findViewById(R.id.listView)
        val profilesList = loadProfilesFromJson()
        // Custom adapter for the ListView
        val adapter = ProfileAdapter(this, R.layout.list_item_layout, profilesList)
        listView.adapter = adapter

        val button = findViewById<ImageButton>(R.id.btnAddContact)
        button.setOnClickListener{
            showBottomSheet()
        }

    }

    private fun loadProfilesFromJson(): List<Profile> {
        val profiles = mutableListOf<Profile>()

        try {
            val json = application.assets.open("jsons/contact.json").bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(json)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val profile = Profile(
                        jsonObject.getString("profileImageResId"),
                        jsonObject.getString("name"),
                        jsonObject.getString("phoneNumber")
                )
                profiles.add(profile)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

        return profiles
    }
    private fun showBottomSheet() {
        val frag = BottomSheetFrag()
        frag.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
        frag.show(supportFragmentManager, frag.tag)
    }
}