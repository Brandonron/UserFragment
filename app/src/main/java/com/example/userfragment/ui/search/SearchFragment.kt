package com.example.userfragment.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.dramaproject.base.viewmodel.BaseViewModelFactory
import com.example.userfragment.R
import com.example.userfragment.adapter.search.SearchAdapterData
import com.example.userfragment.adapter.search.SearchRecyclerAdapter
import com.example.userfragment.adapter.user.OnRecyclerViewItemCallBack
import com.example.userfragment.adapter.user.UserAdapterData
import com.example.userfragment.adapter.user.UserRecyclerAdapter
import com.example.userfragment.api.user.viewmodel.UserApiViewModel
import com.example.userfragment.ui.detail.DetailDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class SearchFragment : Fragment() {

    private lateinit var searchInput: EditText

    private lateinit var userApiViewModel: UserApiViewModel

    private lateinit var searchRecyclerAdapter: SearchRecyclerAdapter

    private lateinit var searchRecyclerView: RecyclerView

    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchInput = view.findViewById(R.id.edit_input_search)
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                userApiViewModel.getSearchList(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        searchRecyclerAdapter =
            SearchRecyclerAdapter(object : OnRecyclerViewItemCallBack<SearchAdapterData> {
                override fun onClick(userAdapterData: SearchAdapterData) {

                    val detailFragment: DetailDialogFragment =
                        DetailDialogFragment(userAdapterData.name)
                    detailFragment.isCancelable = false
                    detailFragment.show(childFragmentManager, "fragmentDialog")

                    Toast.makeText(context, "" + userAdapterData.name, Toast.LENGTH_SHORT).show()
                }
            })

        searchRecyclerView = view.findViewById(R.id.search_recycler)
        searchRecyclerView.adapter = searchRecyclerAdapter

        searchRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                searchRecyclerView.smoothScrollToPosition(searchRecyclerView.top)
            }
        })

        userApiViewModel = ViewModelProviders.of(
            this,
            BaseViewModelFactory { UserApiViewModel() })
            .get(UserApiViewModel::class.java)

        userApiViewModel.searchList.observe(viewLifecycleOwner, Observer {
            var list: ArrayList<SearchAdapterData> = ArrayList<SearchAdapterData>()
            it?.items?.forEach { item ->
                println(item)
                var userAdapterData = SearchAdapterData(
                    item.avatar_url,
                    item.login,
                    item.type
                )
                list.add(userAdapterData)
            }
            searchRecyclerAdapter.updateList(list)

            Toast.makeText(
                context,
                "" + it?.total_count + " / " + it?.incomplete_results,
                Toast.LENGTH_SHORT
            ).show()
        })
    }
}