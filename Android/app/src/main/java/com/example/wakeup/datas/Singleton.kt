package com.example.wakeup.datas

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import com.example.wakeup.model.StudyRecord


object Singleton {
    //var reserveDataList : ArrayList<ReserveData> = ArrayList<ReserveData>()
    var mediaPlayer : MediaPlayer = MediaPlayer()

    var playingPosition : Int = 0

    lateinit var sharedPreferences : SharedPreferences

    var studyRecordList = ArrayList<StudyRecord>()

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
}