package com.example.wakeup.activities

import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.wakeup.R
import com.example.wakeup.adapter.ListViewAdapter
import com.example.wakeup.databinding.ActivityShowUsageStatsBinding
import kotlinx.android.synthetic.main.activity_show_usage_stats.*
import java.lang.String
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class ShowUsageStats : AppCompatActivity() {
    //var usagestatslist : List<com.example.usagestatusAppUsageStatistics.UsageStats> = List<com.example.usagestatusAppUsageStatistics.UsageStats>
    var dailyUsageStatsList = ArrayList<com.example.wakeup.model.UsageStats>()
    var weeklyUsageStatsList = ArrayList<com.example.wakeup.model.UsageStats>()
    var monthlyUsageStatsList = ArrayList<com.example.wakeup.model.UsageStats>()
    var yearlyUsageStatsList = ArrayList<com.example.wakeup.model.UsageStats>()

    var termTest = 0
    var usageStatsList = arrayOf(dailyUsageStatsList, weeklyUsageStatsList, monthlyUsageStatsList, yearlyUsageStatsList)

    lateinit var adapter : ListViewAdapter

    private lateinit var binding : ActivityShowUsageStatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_usage_stats)
        adapter = ListViewAdapter(applicationContext, usageStatsList[0])
        lateinit var queryUsageStats : List<UsageStats>
        //(intervalType: Int, beginTime: Long, endTime: Long)

        if (!checkForPermission()) {
            Log.i("TestLog", "The user may not allow the access to apps usage. ")
            Toast.makeText(this,"권한이 없어요!" ,Toast.LENGTH_LONG).show()
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        } else {
            // We have the permission. Query app usage stats.
            for(i in 0..3){
                queryUsageStats = getAppUsageStats(i)
                usageStatsList[i] = getAppUsageStats(queryUsageStats)
            }
            bindDatas()

            setSpinner(binding)
        }

    }

    private fun bindDatas(){

        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_usage_stats)
        binding.activity = this

        binding.listViewShowUsage.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun setSpinner(binding : ActivityShowUsageStatsBinding){

        val items = resources.getStringArray(R.array.my_array)

        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

        binding.spinner.adapter = myAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                adapter.setDataList(usageStatsList[position])
                adapter.notifyDataSetChanged()
                binding.invalidateAll()
                Log.d("TestLog", "$position")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }
//
//    private fun initListView(binding : ActivityShowUsageStatsBinding , usageStatsList : ArrayList<com.example.wakeup.model.UsageStats>) {
//        adapter.notifyDataSetChanged()
//    }

    private fun checkForPermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), packageName)
        return mode == AppOpsManager.MODE_ALLOWED
    }

    private fun getAppUsageStats(term : Int): MutableList<UsageStats> {
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, -1)    // 1

        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager // 2
        return usageStatsManager.queryUsageStats(term, cal.timeInMillis, System.currentTimeMillis() // 3
        )

    }

    private fun getAppUsageStats(usageStats: MutableList<UsageStats>) : ArrayList<com.example.wakeup.model.UsageStats> {
        var tempUsageStatsList : ArrayList<com.example.wakeup.model.UsageStats> = ArrayList<com.example.wakeup.model.UsageStats>()
        termTest++
        usageStats.forEach { it ->
            var isduplicate = false
            var duplicateIndex = 0
            if(it.totalTimeInForeground > 1000) {
                val usageStats = com.example.wakeup.model.UsageStats("", this?.getDrawable(R.drawable.ic_launcher_foreground)!!, 0)
                usageStats.appName = it.packageName
                try {
                    usageStats.appIcon = packageManager.getApplicationIcon(it.packageName)

                } catch (e: PackageManager.NameNotFoundException) {
                    Log.d(
                            //com.example.android.appusagestatistics.AppUsageStatisticsFragment.TAG
                            "TestLog",
                            String.format(
                                    "App Icon is not found for %s",
                                    it.getPackageName()

                            )
                    )
                    usageStats.appIcon = getDrawable(R.drawable.ic_launcher_foreground)!!
                }

                Log.d(
                        "TestLog",
                        "packageName: ${it.packageName}, lastTimeUsed: ${Date(it.lastTimeUsed)}, totalTimeInForeground: ${it.totalTimeInForeground}"
                )


                usageStats.useTime = (it.totalTimeInForeground / 1000).toInt()

                for( i in 0 until tempUsageStatsList.size)
                {
                    if(tempUsageStatsList[i].appName == usageStats.appName){
                        isduplicate = true
                        duplicateIndex = i
                    }
                }

                if(isduplicate){
                    if(tempUsageStatsList[duplicateIndex].useTime < usageStats.useTime) {
                        tempUsageStatsList[duplicateIndex].useTime = usageStats.useTime
                    }
                }
                else{
                    tempUsageStatsList.add(usageStats)
                }
            }
        }

        tempUsageStatsList.sortWith(Comparator<com.example.wakeup.model.UsageStats> { vo1, vo2 -> vo1?.getTime()!! - vo2?.getTime()!! })
        tempUsageStatsList.reverse()

        //Test용 코드
        //tempUsageStatsList[0].useTime = termTest * 100000

        return tempUsageStatsList
    }
}