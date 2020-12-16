package com.example.wakeup.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.wakeup.R
import com.example.wakeup.datas.Singleton
import com.example.wakeup.network.RetrofitClient
import com.example.wakeup.network.RetrofitInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.login_dialog.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SplashActivity : AppCompatActivity() {

    private lateinit var retrofitInterface: RetrofitInterface
    private var BASE_URL = "http://10.53.68.1:3000"

    lateinit var myAPI: RetrofitInterface
    var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var retrofit: Retrofit? = RetrofitClient().Instance()
        myAPI = retrofit!!.create(RetrofitInterface::class.java)
        initDatas()


        button_gotoMain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        login.setOnClickListener {
            loginUser(email_Edit.text.toString(), pw_Edit.text.toString())
        }
        signIn.setOnClickListener {

        }
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitInterface = retrofit.create(RetrofitInterface::class.java)

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

    private fun loginUser(email: String, pw: String) {
        compositeDisposable.add(
            myAPI.loginUser(email, pw)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer<String>() {
                    
                })
        )
    }

    private fun initDatas() {
        var sf: SharedPreferences = getSharedPreferences("sharedPreferenceFile", MODE_PRIVATE)
        Singleton.setSharedPreference(sf)
        //Singleton.clearData(applicationContext.resources.getString(R.string.user_data))
        //RecordWhatStudied().removeDataSharedPreference(Singleton.getSharedPreference())

        Singleton.itemToDoList =
            Singleton.loadItemTodo(applicationContext.resources.getString(R.string.todo_list))
        Singleton.studyRecordList =
            Singleton.loadStudyRecord(applicationContext.resources.getString(R.string.study_record_list))
        Singleton.userProfile = Singleton.loadUserData(
            applicationContext,
            applicationContext.resources.getString(R.string.user_data)
        )

        //Singleton.clearData(applicationContext.resources.getString(R.string.todo_list))
    }

    // 앱 실행 후 2초 뒤에 MainActivity로 전환
    private fun startLoading() {
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // MainActivity로 화면 전환
            finish() // 꼭 finish()를 해줘야 함
        }, 2000) // 2초 후*/
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}