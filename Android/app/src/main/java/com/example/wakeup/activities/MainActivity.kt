package com.example.wakeup.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.wakeup.R
import com.example.wakeup.navigation.*
import com.example.wakeup.network.LoginResult
import com.example.wakeup.network.RetrofitInterface
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.login_dialog.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var retrofit: Retrofit
    private lateinit var retrofitInterface: RetrofitInterface

    // region BottomNavigation 선택
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
            /*R.id.setting -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frame, SettingFragment())
                transaction.commit()
                return true
            }*/
        }
        return false
    }
    // endregion

    // region onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(this)

        retrofit = Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        retrofitInterface = retrofit.create(RetrofitInterface::class.java)

        login.setOnClickListener {
            handleLoginDialog()
        }
        signIn.setOnClickListener {
            handleSigninDialog()
        }
    }
    // endregion

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

    // region 뒤로 버튼 두 번 누르면 종료
    private var lastTimeBackPressed: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed >= 1500) {
            lastTimeBackPressed = System.currentTimeMillis()
            Toast.makeText(this, "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show()
        } else {
            finish()
        }
    }
    // endregion

    // region 옵션메뉴 보이게!
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    // endregion

    // region 메뉴 아이템 선택 시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent: Intent
        when (item.itemId) {
            R.id.myInfo -> {
                intent = Intent(this, MyInfoActivity::class.java)
                startActivity(intent) // MyInfoActivity로 화면 전환
                finish() // 꼭 finish()를 해줘야 함
            }
            R.id.settings -> {
                intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent) // SettingsActivity로 화면 전환
                finish() // 꼭 finish()를 해줘야 함
            }
        }
        return super.onOptionsItemSelected(item)
    }
    // endregion
}