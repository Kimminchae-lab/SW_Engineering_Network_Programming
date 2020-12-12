package com.example.wakeup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import com.example.wakeup.R
import kotlinx.android.synthetic.main.activity_focusing.*

class FocusingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_focusing)

        button_Return.setOnClickListener {
            finish()
        }

    }

    //region Focus Fragment에서 사용 할 특수 키 이벤트 핸들링
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d("LogTest", "$keyCode")
        when (keyCode) {

            KeyEvent.KEYCODE_BACK -> { Log.d("LogTest", "Back Key Down"); return true }
            KeyEvent.KEYCODE_HOME -> { Log.d("LogTest", "Home Key Down"); return true }
            KeyEvent.KEYCODE_MENU -> { Log.d("LogTest", "Menu Key Pressed"); return true }
            KeyEvent.KEYCODE_APP_SWITCH -> { Log.d("LogTest", "OverView Key Pressed"); return true }
        }
        return super.onKeyDown(keyCode, event)
    }


}
