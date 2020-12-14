package com.example.wakeup.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wakeup.R
import com.example.wakeup.adapter.RecordStudyAdapter
import com.example.wakeup.datas.Singleton
import kotlinx.android.synthetic.main.fragment_time.view.*

class TimeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_time, container, false)

        var adapter = RecordStudyAdapter(context, Singleton.studyRecordList)

        view.listView_ShowStudyRecord.adapter = adapter
        adapter.notifyDataSetChanged()


        return view
    }
}