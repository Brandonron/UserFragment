package com.example.userfragment.adapter.search.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userfragment.R
import com.example.userfragment.adapter.user.OnRecyclerViewItemCallBack
import com.example.userfragment.api.user.response.SearchListResponse
import kotlinx.android.synthetic.main.item_user.view.*

class SearchPageAdapter(
    var onItemClickListener: OnRecyclerViewItemCallBack<SearchListResponse>
) : PagingDataAdapter<SearchListResponse, SearchPageAdapter.ViewHolder>(COMPARATOR) {

    companion object {

        private val COMPARATOR = object : DiffUtil.ItemCallback<SearchListResponse>() {

            override fun areItemsTheSame(
                oldItem: SearchListResponse,
                newItem: SearchListResponse
            ): Boolean {

                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: SearchListResponse,
                newItem: SearchListResponse
            ): Boolean {

                return oldItem == newItem
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(
            recyclerView.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onBindItemViewHolder((holder as ViewHolder), position)
    }

    fun onBindItemViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    inner class ViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding) {
        fun bind(data: SearchListResponse) {
            binding.user_img.setImageURI(data.avatar_url)
            binding.user_name.text = data.login
            binding.user_subname.text = data.type

            binding.setOnClickListener(View.OnClickListener {
                onItemClickListener?.onClick(data)
            })
        }
    }
}