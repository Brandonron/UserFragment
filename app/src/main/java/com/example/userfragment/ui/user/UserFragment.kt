package com.example.userfragment.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.dramaproject.base.viewmodel.BaseViewModelFactory
import com.example.userfragment.R
import com.example.userfragment.adapter.user.OnUserClickListener
import com.example.userfragment.adapter.user.UserAdapterData
import com.example.userfragment.adapter.user.UserRecyclerAdapter
import com.example.userfragment.api.user.viewmodel.UserApiViewModel
import com.example.userfragment.ui.detail.DetailDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UserFragment : Fragment() {

    private lateinit var userRecyclerAdapter: UserRecyclerAdapter

    private lateinit var userRecyclerView: RecyclerView

    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userRecyclerAdapter = UserRecyclerAdapter(view.context,
            ArrayList<UserAdapterData>(),
            object : OnUserClickListener {
                override fun onClick(userAdapterData: UserAdapterData) {

                    val detailFragment: DetailDialogFragment =
                        DetailDialogFragment(userAdapterData.name)
                    detailFragment.isCancelable = false
                    detailFragment.show(childFragmentManager, "fragmentDialog")

                    Toast.makeText(context, "" + userAdapterData.name, Toast.LENGTH_SHORT).show()
                }
            })

        userRecyclerView = view.findViewById(R.id.user_recycler)
        userRecyclerView.adapter = userRecyclerAdapter

        userRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(-1)) {
                    fab.hide();
                } else {
                    fab.show()
                }
            }
        })
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                userRecyclerView.smoothScrollToPosition(userRecyclerView.top)
            }
        })

        apiUserList()

    }

    fun apiUserList() {
        var userApiViewModel: UserApiViewModel = ViewModelProviders.of(
            this,
            BaseViewModelFactory { UserApiViewModel() })
            .get(UserApiViewModel::class.java)

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