package com.example.wakeup.datas

import android.media.MediaPlayer

object Singleton {
    //var reserveDataList : ArrayList<ReserveData> = ArrayList<ReserveData>()
    var mediaPlayer : MediaPlayer = MediaPlayer()

    var playingPosition : Int = 0
}