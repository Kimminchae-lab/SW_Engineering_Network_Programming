package com.example.wakeup.navigation

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wakeup.R
import com.example.wakeup.activities.FocusingActivity
import com.example.wakeup.activities.ShowUsageStats
import com.example.wakeup.datas.Singleton
import com.example.wakeup.datas.Singleton.gMediaPlayer
import com.example.wakeup.datas.Singleton.mediaPlayer
import com.example.wakeup.datas.Singleton.playingPosition
import com.example.wakeup.datas.Singleton.setMusicOfMediaPlayer
import com.github.clans.fab.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_focus.*
import kotlinx.android.synthetic.main.fragment_focus.view.*

data class FabData(
    var fab : FloatingActionButton,
    var icon : Int,
    var mp3 : Int
)


class FocusFragment : Fragment() {

    lateinit var fabDataList: List<FabData>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_focus, container, false)

        initFabDataArray(view)

        isMusicPlaying()



            return view
        }

    private fun setButtonsOnclickListener(view : View){

        for (i in 0..3) {
            fabDataList[i].fab.setOnClickListener {
                controlMusic(i)
            }
        }

    }

    private fun isMusicPlaying(){
        if(gMediaPlayer().isPlaying){
            fabDataList[playingPosition].fab.setImageResource(R.drawable.pause)
        }
    }

    private fun initFabDataArray(view : View){
        fabDataList = listOf(
            FabData(view.fab_Item1,R.drawable.rain,R.raw.mp3_rain),
            FabData(view.fab_Item2, R.drawable.wave, R.raw.mp3_thetawave),
            FabData(view.fab_Item3, R.drawable.fire, R.raw.mp3_fire),
            FabData(view.fab_Item4, R.drawable.piano, R.raw.mp3_piano)
        )
    }

    // region 음악 재생 / 정지
    private fun controlMusic(position: Int,){
        if(!gMediaPlayer().isPlaying){
            startMusic(position)
        }
        else{
            if(position == playingPosition){
                stopMusic(position)
            }
            else{
                changeMusic(position)
            }
        }
    }
    // endregion

    // region 음악 재생
    private fun startMusic(position: Int){
        fabDataList[position].fab.setImageResource(R.drawable.pause)
        //mediaPlayer = MediaPlayer.create(context, fabDataList[position].mp3)
        setMusicOfMediaPlayer(context, fabDataList[position].mp3)

        //mediaPlayer.start()
        gMediaPlayer().start()
        playingPosition = position
    }
    // endregion

    //region 음악 정지
    private fun stopMusic(position: Int){
        gMediaPlayer().stop()
        fabDataList[position].fab.setImageResource(fabDataList[position].icon)
    }


    // region 음악이 재생중일 때 다른 fab 클릭
    fun changeMusic(startPosition: Int){

        gMediaPlayer().stop()
        fabDataList[playingPosition].fab.setImageResource(fabDataList[playingPosition].icon)

        //mediaPlayer = MediaPlayer.create(context, fabDataList[startPosition].mp3)
        setMusicOfMediaPlayer(context, fabDataList[startPosition].mp3)

        fabDataList[startPosition].fab.setImageResource(R.drawable.pause)
        gMediaPlayer().start()
        playingPosition = startPosition

    }

    // endregion


}