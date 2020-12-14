package com.example.wakeup.adapter

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.wakeup.R
import com.example.wakeup.model.UsageStats


class ListViewAdapter(context: Context, arrayList : ArrayList<UsageStats>) : BaseAdapter() {

    var listViewItemList = arrayList
    var context = context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView = inflater.inflate(R.layout.usage_row, parent, false)

        val appIconImageView = convertView?.findViewById<ImageView>(R.id.imageView_appIcon)
        val appNameTextView = convertView?.findViewById<TextView>(R.id.textview_PackageName)
        val totalUseTimeTextView = convertView?.findViewById<TextView>(R.id.textView_TotalUsedTime)

        //appNameTextView?.text = listViewItemList[position].appName
        appIconImageView?.setImageDrawable(listViewItemList[position].appIcon)
        totalUseTimeTextView?.text =  getLeftTime(listViewItemList[position].useTime)



        val pm: PackageManager = context.packageManager
        val ai: ApplicationInfo?
        ai = try {
            pm.getApplicationInfo(listViewItemList[position].appName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
        val applicationName = (if (ai != null) pm.getApplicationLabel(ai) else "(unknown)") as String
        appNameTextView?.text = applicationName

        return convertView
    }
    fun setDataList(arrayList : ArrayList<UsageStats>){
        listViewItemList = arrayList
    }

    private fun getLeftTime(leftTimeSeconds : Int): String{
        var leftTimeString = ""
        var leftTimeSeconds = leftTimeSeconds
        val day = 60 * 60 * 24
        val hour = 60 * 60
        val minute = 60

        if(leftTimeSeconds >= day){
            leftTimeString = "${leftTimeSeconds / day}일"
            leftTimeSeconds %= day
        }
        if(leftTimeSeconds % day >= hour){
            leftTimeString += "${leftTimeSeconds / hour}시간 "
            leftTimeSeconds %= hour
        }
        leftTimeString += "${leftTimeSeconds / minute}분"

        return leftTimeString
    }

    override fun getItem(position: Int): Any {
        return listViewItemList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listViewItemList.size
    }

}