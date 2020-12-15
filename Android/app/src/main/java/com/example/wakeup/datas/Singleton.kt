package com.example.wakeup.datas

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import com.example.wakeup.R
import com.example.wakeup.model.ItemTodo
import com.example.wakeup.model.StudyRecord
import com.example.wakeup.model.UserData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.coroutines.coroutineContext


object Singleton {
    //var reserveDataList : ArrayList<ReserveData> = ArrayList<ReserveData>()
    var mediaPlayer : MediaPlayer = MediaPlayer()

    var playingPosition : Int = 0

    lateinit var userProfile : UserData

    private lateinit var sharedPreferences : SharedPreferences

    var studyRecordList = ArrayList<StudyRecord>()

    var itemToDoList = ArrayList<ItemTodo>()

    fun gMediaPlayer(): MediaPlayer {
        return mediaPlayer
    }

    fun setMusicOfMediaPlayer(context: Context?, resId: Int){
        mediaPlayer = MediaPlayer.create(context, resId)
    }

    fun setSharedPreference(sf: SharedPreferences){
        sharedPreferences = sf
    }
    fun getSharedPreference() : SharedPreferences{
        return sharedPreferences
    }


    fun loadStudyRecord(key: String) : ArrayList<StudyRecord>{
        var itemList =  ArrayList<StudyRecord>()
        var json = getSharedPreference().getString( key, null );
        var gson = Gson()

        val turnsType = object : TypeToken<ArrayList<StudyRecord>>(){}.type
        if(json != null) {
            itemList = gson.fromJson(json, turnsType)
        }
        return itemList
    }

    fun saveStudyRecord(dataList: ArrayList<StudyRecord>, key: String){
        studyRecordList = dataList
        //var pref = getSharedPreferences( "pref", MODE_PRIVATE );
        var pref_editor = getSharedPreference().edit();

        //var itemList = ArrayList<StudyRecord>()
        //itemList.add(StudyRecord(10, "a", "b"))
        //itemList.add(StudyRecord(100, "aa", "bb"))

        var gson = Gson()
        var json = gson.toJson(dataList)

        pref_editor.putString(key, json)
        pref_editor.apply()
    }



    fun loadItemTodo( key: String) : ArrayList<ItemTodo>{
        var itemList =  ArrayList<ItemTodo>()
        var json = getSharedPreference().getString( key, null );
        var gson = Gson()

        val turnsType = object : TypeToken<ArrayList<ItemTodo>>(){}.type
        if(json != null) {
            itemList = gson.fromJson(json, turnsType)
        }
        return itemList
    }

    fun saveItemTodo( dataList: ArrayList<ItemTodo>, key: String){
        itemToDoList = dataList
        //var pref = getSharedPreferences( "pref", MODE_PRIVATE );
        var pref_editor = getSharedPreference().edit();

        //var itemList = ArrayList<ItemTodo>()
        //itemList.add(ItemTodo(10, "a", "b"))
        //itemList.add(ItemTodo(100, "aa", "bb"))

        var gson = Gson()
        var json = gson.toJson(dataList)

        pref_editor.putString(key, json)
        pref_editor.apply()
    }


    fun loadUserData(context: Context?, key: String) : UserData{
        lateinit var userData : UserData
        var json = getSharedPreference().getString( key, null );
        var gson = Gson()

        val turnsType = object : TypeToken<UserData>(){}.type
        if(json != null) {
            userData = gson.fromJson(json, turnsType)
        }else{
            userData = UserData("게스트", BitmapFactory.decodeResource(context?.resources, R.drawable.account_48dp))
        }
        return userData
    }

    fun saveUserData( userData: UserData, key: String){
        userProfile = userData
        var pref_editor = getSharedPreference().edit();

        var gson = Gson()
        var json = gson.toJson(userData)

        pref_editor.putString(key, json)
        pref_editor.apply()
    }
    fun clearData(key: String){
        var pref_editor = getSharedPreference().edit();

        pref_editor.clear()
        pref_editor.apply()

    }

}