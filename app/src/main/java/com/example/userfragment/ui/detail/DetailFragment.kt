package com.example.userfragment.ui.detail

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.userfragment.R
import com.example.userfragment.api.user.UserApiManager
import com.example.userfragment.api.user.viewmodel.UserApiViewModel
import com.example.userfragment.api.user.viewmodel.UserApiViewModelFactory
import com.facebook.drawee.view.SimpleDraweeView


class DetailFragment(val name: String) : DialogFragment() {

    private lateinit var imgClose: ImageView

    private lateinit var userImg: SimpleDraweeView

    private lateinit var userName: TextView
    private lateinit var userSubName: TextView
    private lateinit var userLocal: TextView
    private lateinit var userLink: TextView

    override fun onResume() {
        super.onResume()
        val window = dialog!!.window
        val layoutParams = dialog!!.window!!.attributes
        layoutParams.dimAmount = 0f
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window.setGravity(Gravity.CENTER)
        dialog!!.window!!.attributes = layoutParams
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_NoActionBar)

        // this will make it fullscreen without top status bar
        // setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgClose = view.findViewById(R.id.img_close)
        imgClose.setOnClickListener(View.OnClickListener {
            dismiss()
        })

        userImg = view.findViewById(R.id.img_user)

        userName= view.findViewById(R.id.name)
        userSubName = view.findViewById(R.id.subName)
        userLocal = view.findViewById(R.id.location)
        userLink = view.findViewById(R.id.link)

        apiUserInfo()
    }

    fun apiUserInfo() {
        var userApiViewModel: UserApiViewModel
        userApiViewModel = ViewModelProvider(
            this,
            UserApiViewModelFactory(UserApiManager)
        ).get(UserApiViewModel::class.java)
        userApiViewModel.userInfo.observe(viewLifecycleOwner, Observer {
            userImg.setImageURI(it?.avatar_url)

            userName.text = it?.name
            userSubName.text = it?.login
            userLocal.text = it?.location
            userLink.text = it?.blog
        })
        userApiViewModel.getUserInfo(name)
    }
}