package com.example.userfragment.ui.user

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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

    private lateinit var userApiViewModel: UserApiViewModel

    private lateinit var userRecyclerAdapter: UserRecyclerAdapter

    private lateinit var userRecyclerView: RecyclerView

    private lateinit var fab: FloatingActionButton

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_nav_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setIconifiedByDefault(true)
        searchView.setSubmitButtonEnabled(false)
        searchView.setQueryHint(getString(R.string.search_input_hint));
        searchView.setOnSearchClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                userRecyclerAdapter.setSearchList(null)
            }
        })
        searchView.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
//                userRecyclerAdapter.setUserList(null)
                return false;
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true;
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                userApiViewModel.getSearchList(newText)
                return true;
            }

        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userRecyclerAdapter = UserRecyclerAdapter(object : OnUserClickListener {
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

        userApiViewModel = ViewModelProviders.of(
            this,
            BaseViewModelFactory { UserApiViewModel() })
            .get(UserApiViewModel::class.java)

        userApiViewModel.searchList.observe(viewLifecycleOwner, Observer {
            var list: ArrayList<UserAdapterData> = ArrayList<UserAdapterData>()
            it?.items?.forEach { item ->
                println(item)
                var userAdapterData = UserAdapterData(
                    item.avatar_url,
                    item.login,
                    item.type
                )
                list.add(userAdapterData)
            }
            userRecyclerAdapter.setSearchList(list)

            Toast.makeText(
                context,
                "" + it?.total_count + " / " + it?.incomplete_results,
                Toast.LENGTH_SHORT
            ).show()
        })

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