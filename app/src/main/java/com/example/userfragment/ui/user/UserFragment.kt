package com.example.userfragment.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.userfragment.R
import com.example.userfragment.adapter.user.OnUserClickListener
import com.example.userfragment.adapter.user.UserAdapterData
import com.example.userfragment.adapter.user.UserRecyclerAdapter
import com.example.userfragment.api.user.UserApiManager
import com.example.userfragment.api.user.viewmodel.UserApiViewModel
import com.example.userfragment.api.user.viewmodel.UserApiViewModelFactory
import com.example.userfragment.ui.detail.DetailFragment

class UserFragment : Fragment() {

    private lateinit var userRecyclerAdapter  : UserRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_user, container, false)

        userRecyclerAdapter = UserRecyclerAdapter(root.context,
            ArrayList<UserAdapterData>(),
            object : OnUserClickListener {
                override fun onClick(userAdapterData: UserAdapterData) {

                    val detailFragment: DetailFragment = DetailFragment(userAdapterData.name)
                    detailFragment.isCancelable = false
                    detailFragment.show(childFragmentManager, "fragmentDialog")

                    Toast.makeText(context, "" + userAdapterData.name, Toast.LENGTH_SHORT).show()
                }
            })

        var userRecyclerView: RecyclerView = root.findViewById(R.id.user_recycler)
        userRecyclerView.adapter = userRecyclerAdapter

        apiUserList()

        return root
    }

    fun apiUserList() {
        var userApiViewModel: UserApiViewModel
        userApiViewModel = ViewModelProvider(
            this,
            UserApiViewModelFactory(UserApiManager)
        ).get(UserApiViewModel::class.java)
        userApiViewModel.userList.observe(viewLifecycleOwner, Observer {
            var list: ArrayList<UserAdapterData> = ArrayList<UserAdapterData>()
            it?.forEach { item ->
                println(item)
                var userAdapterData = UserAdapterData(
                    item.avatar_url,
                    item.login,
                    item.type
                )
                list.add(userAdapterData)
            }
            userRecyclerAdapter.setUserList(list)
        })
        userApiViewModel.getUserList()
    }
}