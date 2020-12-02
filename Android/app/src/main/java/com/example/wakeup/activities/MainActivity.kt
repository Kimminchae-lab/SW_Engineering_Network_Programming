package com.example.wakeup.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.wakeup.R

class MainActivity : AppCompatActivity() {
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.action_main -> {
                val transaction =
                    supportFragmentManager.beginTransaction() transaction . replace (R.id.frame, MainFragment()) transaction.commit() return true
            }
                ... R.id.action_account
            -> {
                val transaction =
                    supportFragmentManager.beginTransaction() transaction . replace (R.id.frame, AccountFragment()) transaction.commit() return true
            }
        } return false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}