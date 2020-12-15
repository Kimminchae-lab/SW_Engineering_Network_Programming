package com.example.wakeup

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.graphics.drawable.toBitmap
import com.example.wakeup.datas.Singleton
import com.example.wakeup.model.UserData
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.io.IOException

class EditProfile : AppCompatActivity() {

    lateinit var imageBitmap: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        imageBitmap = Singleton.stringToBitmap(Singleton.userProfile.profileImage)!!
        init()
        hideActionBar()

        setButtonsOnClick()

    }

    private fun init() {
        editText_Edit_ProfileName.setText(Singleton.userProfile.proFileName)
        //imageView_Edit_ProfileImage.setImageBitmap(Singleton.userProfile.profileImage)
        textView_Edit_ProfileImage.setOnClickListener {
            openGallery()
        }
    }

    private fun setButtonsOnClick() {
        imageView_Cancel_EditProfile.setOnClickListener {
            finish()
        }
        imageView_Finish_EditProfile.setOnClickListener {
            Singleton.saveUserData(UserData(editText_Edit_ProfileName.text.toString(), Singleton.bitmapToString(imageBitmap)!! ), applicationContext.resources.getString(R.string.user_data))
            setResult(100)
            finish()
        }
    }

    private fun hideActionBar() {
        // region hide actionBar
        val actionBar = supportActionBar
        actionBar?.hide()

        // endregion
    }

    //region 프로필 사진 변경
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

            val imageBitmap = MediaStore.Images.Media.getBitmap(applicationContext.contentResolver, imageUri)

            try {
                Singleton.userProfile.profileImage = Singleton.bitmapToString(imageBitmap)!!
            } catch (ex: IOException) {
                ex.printStackTrace()
            }

        }
    }

    //endregion

}