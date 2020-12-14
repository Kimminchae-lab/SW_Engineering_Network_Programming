package com.example.wakeup.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.wakeup.R
import com.example.wakeup.datas.Singleton
import com.example.wakeup.network.LoginResult
import com.example.wakeup.network.RetrofitInterface
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.login_dialog.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SplashActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var retrofitInterface: RetrofitInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        initDatas()


        button_gotoMain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        login.setOnClickListener {
            handleLoginDialog()
        }
        signIn.setOnClickListener {
            handleSigninDialog()
        }

        // region hide actionBar
        val actionBar = supportActionBar
        actionBar?.hide()
        // endregion

        // region hide titleBar(fullScreen)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        // endregion

        startLoading()
    }

    private fun initDatas() {
        var sf : SharedPreferences = getSharedPreferences("sharedPreferenceFile",MODE_PRIVATE);
        Singleton.setSharedPreference(sf)

        //RecordWhatStudied().removeDataSharedPreference(Singleton.getSharedPreference())

        Singleton.studyRecordList = RecordWhatStudied().loadDataSharedPreference(Singleton.getSharedPreference())

        for (i in 0 until Singleton.studyRecordList.size) {
            Log.d(
                   "LogTest",
                  "studyTime = ${Singleton.studyRecordList[i].studyTime}" +
                        "summmary = ${Singleton.studyRecordList[i].summary}" +
                        "details = ${Singleton.studyRecordList[i].details}"
            )
        }
    }

    // 앱 실행 후 2초 뒤에 MainActivity로 전환
    private fun startLoading() {
        // 로그인이 되어있는 상태라면
        /*Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // MainActivity로 화면 전환
            finish() // 꼭 finish()를 해줘야 함
        }, 2000) // 2초 후*/
        // 로그인이 되지 않았을 시
        login.isVisible = true
        signIn.isVisible = true
    }

    private fun handleLoginDialog() {
        var view = layoutInflater.inflate(R.layout.login_dialog, null)
        var builder = AlertDialog.Builder(this)
        builder.setView(view).show()

        // region loginBtn.setOnClickListener
        loginBtn.setOnClickListener {
            var map: HashMap<String, String> = HashMap()

            map["email"] = email_Edit.text.toString()
            map["password"] = pw_Edit.text.toString()

            var call: Call<LoginResult> = retrofitInterface.executeLogin(map)

            call.enqueue(object : Callback<LoginResult> {
                override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
                    TODO("Not yet implemented")
                }

                override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
        // endregion
    }

    // region Sign In
    private fun handleSigninDialog() {
        TODO("Not yet implemented")
    }
    // endregion


    private fun logedIn() {

    }
}