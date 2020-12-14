package com.example.wakeup.activities

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.wakeup.R
import com.example.wakeup.adapter.RecordStudyAdapter
import com.example.wakeup.databinding.ActivityRecordWhatStudiedBinding
import com.example.wakeup.datas.Singleton
import com.example.wakeup.model.StudyRecord
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_record_what_studied.*
import java.time.LocalDate


class RecordWhatStudied : AppCompatActivity() {

    private lateinit var binding: ActivityRecordWhatStudiedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_what_studied)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_record_what_studied)
        binding.activity = this

        if (intent.getStringExtra("Route") == "Add" ){
            addDataToStudyRecordList()
            Log.d("TestLog", "Add 완료!")
        }
        else if (intent.getStringExtra("Route") == "Edit"){
            editDataStudyRecordList()
            Log.d("TestLog", "Edit 완료!")
        }
    }

    private fun editDataStudyRecordList() {
        button_Record.text = "수정하기"

        editText_Record_StudySummary.setText( Singleton.studyRecordList[intent.getIntExtra("Position", 0)].summary )
        editText_Record_StudyDetails.setText( Singleton.studyRecordList[intent.getIntExtra("Position", 0)].details )

        binding.buttonCancle.setOnClickListener {
            finishActivity(MainActivity(), "Edit")
        }
        binding.buttonRecord.setOnClickListener {

            //Singleton.studyRecordList = loadDatasSharedPreference(Singleton.getSharedPreferenceFile())

            Singleton.studyRecordList[intent.getIntExtra("Position", 0)].summary = editText_Record_StudySummary.text.toString()
            Singleton.studyRecordList[intent.getIntExtra("Position", 0)].details = editText_Record_StudyDetails.text.toString()


            saveDataSharedPreference(Singleton.sharedPreferences, Singleton.studyRecordList)

            for (i in 0 until Singleton.studyRecordList.size) {
                Log.d(
                    "LogTest", "studyTime = ${Singleton.studyRecordList[i].studyTime}  " +
                            "summmary = ${Singleton.studyRecordList[i].summary}  " +
                            "details = ${Singleton.studyRecordList[i].details}  " +
                            "studyDate = ${Singleton.studyRecordList[i].studyDate}  "
                )
            }
            var intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("Route", "Edit")
            startActivity(intent)
            binding.invalidateAll()
            finish()
            Toast.makeText(applicationContext, "학습 내용 기록했습니다.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun addDataToStudyRecordList() {
        binding.buttonCancle.setOnClickListener {
            finishActivity(MainActivity(), "Add")
        }
        binding.buttonRecord.setOnClickListener {

            var studyRecord = StudyRecord(
                intent.getIntExtra("Time", 0), editText_Record_StudySummary.text.toString(), editText_Record_StudyDetails.text.toString(), "${LocalDate.now()}"
            )

            Singleton.studyRecordList = loadDataSharedPreference(Singleton.getSharedPreference())

            Singleton.studyRecordList.add(studyRecord)


            saveDataSharedPreference(Singleton.sharedPreferences, Singleton.studyRecordList)

            for (i in 0 until Singleton.studyRecordList.size) {
                Log.d(
                    "LogTest", "studyTime = ${Singleton.studyRecordList[i].studyTime}  " +
                            "summmary = ${Singleton.studyRecordList[i].summary}  " +
                            "details = ${Singleton.studyRecordList[i].details}  " +
                            "studyDate = ${Singleton.studyRecordList[i].studyDate}  "
                )
            }

            startActivity(Intent(applicationContext, MainActivity::class.java))
            binding.invalidateAll()
            finish()
            Toast.makeText(applicationContext, "학습 내용 기록했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

     fun loadDataSharedPreference(pref: SharedPreferences) : ArrayList<StudyRecord>{
        var itemList =  ArrayList<StudyRecord>()
        var json = pref.getString( "list", null );
        var gson = Gson()

        val turnsType = object : TypeToken<ArrayList<StudyRecord>>(){}.type
         if(json != null) {
             itemList = gson.fromJson(json, turnsType)
         }
        return itemList
     }

    private fun saveDataSharedPreference( pref : SharedPreferences ,dataList: ArrayList<StudyRecord>){
        //var pref = getSharedPreferences( "pref", MODE_PRIVATE );
        var pref_editor = pref.edit();

        //var itemList = ArrayList<StudyRecord>()
        //itemList.add(StudyRecord(10, "a", "b"))
        //itemList.add(StudyRecord(100, "aa", "bb"))

        var gson = Gson()
        var json = gson.toJson(dataList)

        pref_editor.putString("list", json)
        pref_editor.apply()
    }

    fun removeDataSharedPreference(pref: SharedPreferences){
        var pref_editor = pref.edit();

        pref_editor.clear()
        pref_editor.apply()

        Singleton.studyRecordList = loadDataSharedPreference(Singleton.getSharedPreference())
    }

    private fun finishActivity(activity: Activity, string:String){
        var intent = Intent(applicationContext, activity::class.java)
        intent.putExtra("Route", string)
        startActivity(intent)
        binding.invalidateAll()
        finish()
        Toast.makeText(applicationContext, "취소했습니다.", Toast.LENGTH_SHORT).show()
    }

}