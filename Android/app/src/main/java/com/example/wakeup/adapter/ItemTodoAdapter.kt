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
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.example.wakeup.R
import com.example.wakeup.model.ItemTodo


class ItemTodoAdapter(context: Context, arrayList : ArrayList<ItemTodo>): BaseAdapter() {

    var itemToDoList = arrayList
    var context = context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var convertView = inflater.inflate(R.layout.item_todo, parent, false)

        var checkBox = convertView.findViewById<CheckBox>(R.id.checkBox_item_Todo)
        var textTodo = convertView.findViewById<TextView>(R.id.textView_item_Todo)


        checkBox.isChecked = itemToDoList[position].isChecked
        textTodo.text = itemToDoList[position].text
        checkCheckBox(checkBox, textTodo)

        checkBox.setOnClickListener {
            checkCheckBox(checkBox, textTodo)
        }

        convertView.setOnClickListener {
            makeEditDialog(position)
        }

        return convertView
    }

    private fun makeEditDialog(position: Int){
        val alert: AlertDialog.Builder = AlertDialog.Builder(context)

        alert.setTitle("학습목표 수정")
        alert.setView(R.layout.add_todo_item_dialog)
        
        alert.setPositiveButton("수정",
            DialogInterface.OnClickListener { dialog, whichButton ->
                val f: Dialog = dialog as Dialog
                var input = f.findViewById(R.id.addboxdialog) as EditText
                input.setText(itemToDoList[position].text)
                val value = input.text.toString()
                //value.toString();

                itemToDoList[position].text = value


                notifyDataSetChanged()
                // Do something with value!

            })


        alert.setNegativeButton("취소",
            DialogInterface.OnClickListener { dialog, whichButton ->
                // Canceled.
            })

        val dialog: AlertDialog = alert.create()
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        alert.show()
    }

    private fun checkCheckBox(checkBox: CheckBox, textTodo: TextView) {
        if(checkBox.isChecked){
            textTodo.paintFlags = textTodo.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            textTodo.setTextColor(Color.parseColor("#FF919191"))

        }else if (!checkBox.isChecked){
            textTodo.paintFlags = 0
            textTodo.setTextColor(Color.parseColor("#000000"))

        }
    }

    fun addTodoItemtoArrayList(itemTodo: ItemTodo){
        itemToDoList.add(itemTodo)
    }

    override fun getItem(position: Int): Any {
        return itemToDoList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return itemToDoList.size
    }

}