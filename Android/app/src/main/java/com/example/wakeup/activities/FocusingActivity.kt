package com.example.wakeup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.databinding.DataBindingUtil
import com.example.wakeup.R
import com.example.wakeup.databinding.ActivityFocusingBinding
import kotlinx.android.synthetic.main.activity_focusing.*
import kotlin.concurrent.timer

class FocusingActivity : AppCompatActivity() {

    var i = 0
    var focusingTime = ""

    private lateinit var binding : ActivityFocusingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_focusing)



        bindFocusingTIme()


        updateFocusingTime()


    }

    private fun bindFocusingTIme() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_focusing)
        binding.activity = this
        binding.buttonStopFocusing.setOnClickListener {
            var intent = Intent(applicationContext, RecordWhatStudied::class.java)
            intent.putExtra("Time", i)
            intent.putExtra("Route", "Add")
            startActivity(intent)
            finish()
            binding.invalidateAll()
        }
        binding.invalidateAll()
        //binding.textViewFocusingTime.text = focusingTime

    }

    private fun updateFocusingTime(){
        var minute = 1
        var hour =  60

        val timer = timer(period = 1000) {
            // 실행 코드

            focusingTime = "${String.format("%02d", (++i / hour))} : ${String.format("%02d", ((i %hour)/minute))}"
            //textView_FocusingTime.text = focusingTime

            binding.invalidateAll()
        }
    }

  /*  //region Focus Fragment에서 사용 할 특수 키 이벤트 핸들링
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
*/

}
