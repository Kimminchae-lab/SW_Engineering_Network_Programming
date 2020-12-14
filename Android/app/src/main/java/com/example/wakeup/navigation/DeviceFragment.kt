package com.example.wakeup.navigation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.wakeup.R
import com.example.wakeup.activities.FocusingActivity
import com.example.wakeup.activities.ShowUsageStats
import kotlinx.android.synthetic.main.fragment_device.view.*

class DeviceFragment : Fragment() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
    var myactivity: FragmentActivity? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
          myactivity = activity
        var view = inflater.inflate(R.layout.fragment_device, container, false)

        setButtonsOnclickListener(view)

        return view

    }

    private fun setButtonsOnclickListener(view : View){

        view.button_Focus.setOnClickListener {
            startActivity(Intent(activity, FocusingActivity::class.java))
        }

        view.button_showUsage.setOnClickListener {
            startActivity(Intent(activity, ShowUsageStats::class.java))
        }
    }
}