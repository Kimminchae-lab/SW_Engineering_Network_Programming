package com.example.wakeup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.view.isVisible
import com.example.wakeup.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // region hide actionBar
        val actionBar = supportActionBar
        actionBar?.hide()
        // endregion

        startLoading()
    }

    // 앱 실행 후 2초 뒤에 MainActivity로 전환
    private fun startLoading() {
        // 로그인이 되어있는 상태라면
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // MainActivity로 화면 전환
            finish() // 꼭 finish()를 해줘야 함
        }, 2000) // 2초 후
        // 로그인이 되지 않았을 시
        loginButton.isVisible = true
    }
}