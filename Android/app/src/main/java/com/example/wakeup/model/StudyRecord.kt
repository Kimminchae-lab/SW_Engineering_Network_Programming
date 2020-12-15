package com.example.wakeup.model

import android.content.SharedPreferences
import com.example.wakeup.datas.Singleton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate

data class StudyRecord (
    var studyTime : Int,
    var summary : String,
    var details: String,
    var studyDate: String
)


