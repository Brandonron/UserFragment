package com.example.userfragment.ui.detail

import android.os.Bundle
import android.view.*
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.dramaproject.base.viewmodel.BaseViewModelFactory
import com.example.userfragment.R
import com.example.userfragment.api.user.viewmodel.UserApiViewModel
import com.facebook.drawee.view.SimpleDraweeView

class DetailDialogFragment(val name: String) : BaseDialogFragment() {

    private lateinit var imgClose: ImageView

    private lateinit var userImg: SimpleDraweeView

    private lateinit var userName: TextView
    private lateinit var userSubName: TextView
    private lateinit var userLocal: TextView
    private lateinit var userLink: TextView

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

        userName = view.findViewById(R.id.name)
        userSubName = view.findViewById(R.id.subName)
        userLocal = view.findViewById(R.id.location)
        userLink = view.findViewById(R.id.link)

        apiUserInfo()
    }

    fun apiUserInfo() {
        var userApiViewModel: UserApiViewModel = ViewModelProviders.of(
            this,
            BaseViewModelFactory { UserApiViewModel() })
            .get(UserApiViewModel::class.java)

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