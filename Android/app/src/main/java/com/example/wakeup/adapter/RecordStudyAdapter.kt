package com.example.wakeup.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.wakeup.R
import com.example.wakeup.activities.RecordWhatStudied
import com.example.wakeup.model.StudyRecord
import com.example.wakeup.model.UsageStats

class RecordStudyAdapter(context: Context?, arrayList : ArrayList<StudyRecord>) : BaseAdapter() {

    var arrayListStudyRecord = arrayList
    var context = context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        val inflater : LayoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView = inflater.inflate(R.layout.item_record_study, parent, false)

        val studyTimeTextView = convertView?.findViewById<TextView>(R.id.textView_Show_StudyTime)
        val whenStudyTextView = convertView?.findViewById<TextView>(R.id.textView_Show_WhenStudy)
        val summaryTextView = convertView?.findViewById<TextView>(R.id.textView_Show_Summary)


        var studyT = arrayListStudyRecord[position].studyTime
        var minute = 1
        var hour =  60

        studyTimeTextView?.text = "${String.format("%02d", (studyT / hour))} : ${String.format("%02d", ((studyT %hour)/minute))}"
        whenStudyTextView?.text = arrayListStudyRecord[position].studyDate


        summaryTextView?.text =
            if(arrayListStudyRecord[position].summary != ""){
                arrayListStudyRecord[position].summary
            }else{
                "한 줄 요약을 입력하지 않으셨습니다!"
            }

        convertView.setOnClickListener {
            var intent = Intent(context,  RecordWhatStudied::class.java)
            intent.putExtra("Route", "Edit")
            intent.putExtra("Position", position)
            context?.startActivity(intent)
        }

        return convertView
    }
    fun setDataList(arrayList : ArrayList<StudyRecord>){
        arrayListStudyRecord = arrayList
    }



    override fun getItem(position: Int): Any {
        return arrayListStudyRecord[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayListStudyRecord.size
    }

}