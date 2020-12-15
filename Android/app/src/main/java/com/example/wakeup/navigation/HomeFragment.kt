package com.example.wakeup.navigation

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.wakeup.R
import com.example.wakeup.databinding.FragmentHomeBinding
import com.example.wakeup.datas.Singleton

open class HomeFragment : Fragment() {
    lateinit var profileImage : Bitmap
    var profileName : String = ""
    lateinit var binding : FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container,false)
        binding.activity = this

        setDatas()

        var v = binding.root

        return v
    }

    fun setDatas() {
        //view.imageView_Profile_Image.setImageBitmap(Singleton.userProfile.profileImage)
        profileImage = Singleton.userProfile.profileImage
        profileName= Singleton.userProfile.proFileName
    }

    @BindingAdapter("android:bindBitmap")
    open fun setImageViewBitmap(imageView: ImageView, bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
    }

}


