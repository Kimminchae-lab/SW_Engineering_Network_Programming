package com.example.wakeup.navigation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.wakeup.R

import com.example.wakeup.datas.Singleton
import com.example.wakeup.model.UserData
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.io.IOException

open class HomeFragment : Fragment() {

    //lateinit var profileImage : Bitmap
    //var profileName : String = ""

    lateinit var profileImageView: ImageView
    lateinit var profileTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        setProfile(view)

        return view
    }

    fun setProfile(view: View) {

        //profileImage = Singleton.userProfile.profileImage
        //profileName = Singleton.userProfile.proFileName

        profileImageView = view.findViewById(R.id.imageView_Profile_Image)
        profileTextView = view.findViewById(R.id.textView_Profile_Name)

        Glide.with(view.context)
            .load(Singleton.stringToBitmap(Singleton.userProfile.profileImage))
            .override(80, 80)
            .into(profileImageView)

        view.imageView_Profile_Image.setOnClickListener {
            openGallery()
        }

        updateProfileDatas(view)

    }

    fun updateMyProfile(){
        Glide.with(requireView())
            .load(Singleton.stringToBitmap(Singleton.userProfile.profileImage))
            .into(profileImageView)

        profileTextView.text = Singleton.userProfile.proFileName
    }

    fun updateProfileDatas(view: View){
        var totalGoalNum = 0
        var focusTime = 0

        for(i in 0 until Singleton.itemToDoList.size){
            if(Singleton.itemToDoList[i].isChecked){
                totalGoalNum++
            }
        }
        view.textview_Profile_TotalGoalNum.text = totalGoalNum.toString()

        for(i in 0 until Singleton.studyRecordList.size){
            focusTime += Singleton.studyRecordList[i].studyTime
        }

        view.textView_Profile_FocusNum.text = Singleton.studyRecordList.size.toString()
        view.textView_Profile_FocusTotalTime.text = "${String.format("%02d", (focusTime / 60))} : ${String.format("%02d", ((focusTime %60)/1))}"
    }

    private fun openGallery() {
        // 암시적 인텐트로 갤러리 열기
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 100){
            val imageUri : Uri? = data?.data

            //Uri 제대로 왔는지 테스트하는 코드
            //imageView.setImageURI(imageUri)

            val imageBitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, imageUri)

            try {
                 Glide.with(requireView())
                     .load(imageBitmap)
                     .into(profileImageView)

                //profileImageView.setImageBitmap(imageBitmap)
                Singleton.saveUserData(UserData(Singleton.userProfile.proFileName, Singleton.bitmapToString(imageBitmap)!!), requireContext().resources.getString(R.string.user_data))
            } catch (ex: IOException) {
                ex.printStackTrace()
            }

        }
    }

}
//
//@BindingAdapter("android:bindBitmap")
// fun setImageViewBitmap(imageView: ImageView, uri: Uri) {
//    imageView.setImageURI(uri)
//}


