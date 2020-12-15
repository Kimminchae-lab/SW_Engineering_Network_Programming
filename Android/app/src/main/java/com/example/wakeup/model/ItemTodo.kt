package com.example.wakeup.model

import android.content.SharedPreferences
import com.example.wakeup.datas.Singleton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class ItemTodo (
    var isChecked: Boolean,
    var text : String
){


}

