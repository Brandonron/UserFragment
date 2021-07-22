package com.example.userfragment.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.dramaproject.base.viewmodel.BaseViewModelFactory
import com.example.userfragment.R
import com.example.userfragment.api.user.UserApiManager
import com.example.userfragment.api.user.viewmodel.UserApiViewModel
import com.facebook.drawee.view.SimpleDraweeView

class MineFragment : Fragment() {

    private lateinit var userImg: SimpleDraweeView

    private lateinit var userName: TextView
    private lateinit var userSubName: TextView
    private lateinit var userFollowInfo: TextView
    private lateinit var userEmail: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_mine, container, false)

        userImg = root.findViewById(R.id.img_user)

        userName = root.findViewById(R.id.name)
        userSubName = root.findViewById(R.id.subName)
        userFollowInfo = root.findViewById(R.id.follow)
        userEmail = root.findViewById(R.id.email)

        apiUser()

        return root
    }

    fun apiUser() {
        var userApiViewModel: UserApiViewModel = ViewModelProviders.of(
            this,
            BaseViewModelFactory { UserApiViewModel() })
            .get(UserApiViewModel::class.java)

        userApiViewModel.user.observe(viewLifecycleOwner, Observer {
            userImg.setImageURI(it.avatar_url)

            userName.text = (it.name)
            userSubName.text = (it.login)
            userFollowInfo.text = "" + it.followers + " follows ‧ " + it.following + " following"
            userEmail.text = it.email

        })
        userApiViewModel.getUser()
    }
}