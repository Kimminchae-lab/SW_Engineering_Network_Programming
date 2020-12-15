package com.example.wakeup.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.wakeup.R
import com.example.wakeup.datas.Singleton
import com.example.wakeup.network.LoginResult
import com.example.wakeup.network.RetrofitInterface
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.login_dialog.*
import kotlinx.android.synthetic.main.signup_dialog.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SplashActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var retrofitInterface: RetrofitInterface
    private var BASE_URL = "http://10.53.68.1:3000"
    var logedIn: Int = 0
    var signedIn: Int = 0

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

    private fun initDatas() {
        var sf: SharedPreferences = getSharedPreferences("sharedPreferenceFile", MODE_PRIVATE);
        Singleton.setSharedPreference(sf)

        //RecordWhatStudied().removeDataSharedPreference(Singleton.getSharedPreference())

        Singleton.itemToDoList = Singleton.loadItemTodo(applicationContext.resources.getString(R.string.todo_list))
        Singleton.studyRecordList = Singleton.loadStudyRecord( applicationContext.resources.getString(R.string.study_record_list))
        Singleton.userProfile = Singleton.loadUserData(applicationContext, applicationContext.resources.getString(R.string.user_data))

        //Singleton.clearData(applicationContext.resources.getString(R.string.todo_list))
    }

    // 앱 실행 후 2초 뒤에 MainActivity로 전환
    private fun startLoading() {
        /*Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // MainActivity로 화면 전환
            finish() // 꼭 finish()를 해줘야 함
        }, 2000) // 2초 후*/
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
                    if (response.code() == 200) {
                        // var result = response.body()
                        logedIn = 1
                        Toast.makeText(this@SplashActivity, "로그인 성공", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 404) {
                        logedIn = 2
                        Toast.makeText(this@SplashActivity, "Wrong Credentials", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                    logedIn = 3
                    Toast.makeText(this@SplashActivity, t.message, Toast.LENGTH_LONG).show()
                }

            })
        }
        // endregion
    }

    // region Sign In
    private fun handleSigninDialog() {
        var view = layoutInflater.inflate(R.layout.signup_dialog, null)
        var builder = AlertDialog.Builder(this)
        builder.setView(view).show()

        signupBtn.setOnClickListener {
            var map: HashMap<String, String> = HashMap()

            map["name"] = name_Edit.text.toString()
            map["email"] = email_Edit.text.toString()
            map["password"] = pw_Edit.text.toString()

            var call: Call<Void> = retrofitInterface.executeSignup(map)

            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.code() == 200) {
                        signedIn = 1
                        Toast.makeText(this@SplashActivity, "Signed up", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 400) {
                        signedIn = 2
                        Toast.makeText(this@SplashActivity, "Already registered", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    signedIn = 3
                    Toast.makeText(this@SplashActivity, t.message, Toast.LENGTH_LONG).show()
                }
            });
        }
    }
// endregion


}