package com.example.wakeup.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.wakeup.R
import com.example.wakeup.navigation.*
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.home -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frame, HomeFragment())
                transaction.commit()
                return true
            }
            R.id.focus -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frame, FocusFragment())
                transaction.commit()
                return true
            }
            R.id.device -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frame, DeviceFragment())
                transaction.commit()
                return true
            }
            R.id.nosleep -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frame, NoSleepFragment())
                transaction.commit()
                return true
            }
            R.id.time -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frame, TimeFragment())
                transaction.commit()
                return true
            }
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(this)
    }

    var lastTimeBackPressed: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed >= 1500) {
            lastTimeBackPressed = System.currentTimeMillis()
            Toast.makeText(this, "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show()
        } else {
            finish()
        }
    }
}