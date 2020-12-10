package com.example.wakeup.navigation

//import com.github.clans.fab.FloatingActionButton
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wakeup.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_focus.view.*


class FocusFragment : Fragment() {

    lateinit var mediaPlayer: MediaPlayer
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_focus, container, false)


        view.fab_Item1.setOnClickListener {
            startMusic(view.fab_Item1 as FloatingActionButton, R.raw.sad)
        }
        view.fab_Item1.setOnClickListener {
            var mediaPlayer = MediaPlayer.create(context, R.raw.sad)
            mediaPlayer.start()
        }



        return view
    }

    fun startMusic(view: FloatingActionButton, musicId: Int) {
        //val fabIcon = BitmapFactory.decodeResource(resources, R.drawable.fab_add)

        //view.setImageBitmap(fabIcon)
        view.setImageResource(R.drawable.baseline_headset_black_18dp)
        var mediaPlayer = MediaPlayer.create(context, musicId)
        mediaPlayer.start()
    }

    fun cantExit() {

    }

}