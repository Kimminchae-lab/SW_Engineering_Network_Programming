package com.example.wakeup.adapter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import com.example.wakeup.R
import com.example.wakeup.datas.Singleton
import com.example.wakeup.model.ItemTodo


class ItemTodoAdapter(context: Context): BaseAdapter() {

    
    var context = context
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var convertView = inflater.inflate(R.layout.item_todo, parent, false)

        var checkBox = convertView.findViewById<CheckBox>(R.id.checkBox_item_Todo)
        var textTodo = convertView.findViewById<TextView>(R.id.textView_item_Todo)


        checkBox.isChecked = Singleton.itemToDoList[position].isChecked
        textTodo.text = Singleton.itemToDoList[position].text

        updateCheckBox(checkBox, textTodo, position)

        checkBox.setOnClickListener {
            updateCheckBox(checkBox, textTodo, position)
        }

        convertView.setOnClickListener {
            if(!checkBox.isChecked) {
                makeEditDialog(position)
            }
        }

        return convertView
    }

    private fun makeEditDialog(position: Int){
        val alert: AlertDialog.Builder = AlertDialog.Builder(context)

        alert.setTitle("학습목표 수정")
        alert.setView(R.layout.add_todo_item_dialog)
        alert.setPositiveButton("수정"
        ) { dialog, whichButton ->
            val f: Dialog = dialog as Dialog
            var input = f.findViewById(R.id.addboxdialog) as EditText
            val value = input.text.toString()
            //value.toString();

            if(value == ""){
                Toast.makeText(context, "공백으로 수정할수 없어요!", Toast.LENGTH_SHORT).show()
            }else{
                Singleton.itemToDoList[position].text = value
                notifyDataSetChanged()
            }



            // Do something with value!

        }
        alert.setNegativeButton("취소",
            DialogInterface.OnClickListener { dialog, whichButton ->
                // Canceled.
            })

        val dialog: AlertDialog = alert.create()
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        alert.show()
    }

    private fun updateCheckBox(checkBox: CheckBox, textTodo: TextView, position: Int) {
        Log.d("TestLog", "Check!")
        if(checkBox.isChecked){
            Log.d("TestLog", "Check -> UnCheck")
            textTodo.paintFlags = textTodo.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            textTodo.setTextColor(Color.parseColor("#FF919191"))
            checkBox.isEnabled = false
        }else if (!checkBox.isChecked){
            Log.d("TestLog", "Uncheck -> Check")
            textTodo.paintFlags = 0
            textTodo.setTextColor(Color.parseColor("#000000"))
        }
        //notifyDataSetChanged()
        Singleton.itemToDoList[position] = ItemTodo(checkBox.isChecked, textTodo.text.toString())

        //Singleton.itemToDoList.sortWith(Comparator<com.example.wakeup.model.ItemTodo> { vo1, vo2 -> vo1?.isChecked!! - vo2?.isChecked!! })

        var sortedList =  Singleton.itemToDoList.sortedBy { Singleton.itemToDoList[position].isChecked }

        for(i in 0 until  sortedList.size){
            Singleton.itemToDoList[i] = sortedList[i]
        }

            Singleton.saveItemTodo(Singleton.itemToDoList, context.resources.getString(R.string.todo_list))

    }

    fun addTodoItemtoArrayList(itemTodo: ItemTodo){
        Singleton.itemToDoList.add(itemTodo)
    }

    override fun getItem(position: Int): Any {
        return Singleton.itemToDoList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return Singleton.itemToDoList.size
    }

}