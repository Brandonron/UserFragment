package com.example.userfragment.adapter.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dramaproject.adapter.base.EmptyAdapter
import com.example.userfragment.R
import com.example.userfragment.adapter.user.OnRecyclerViewItemCallBack
import kotlinx.android.synthetic.main.item_user.view.*

class SearchRecyclerAdapter(
    var onItemClickListener: OnRecyclerViewItemCallBack<SearchAdapterData>
) : EmptyAdapter() {
    var arrayList: MutableList<SearchAdapterData> = mutableListOf()

    fun updateList(list: ArrayList<SearchAdapterData>?) {
        arrayList = list!!.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (arrayList!!.isEmpty())
            super.getItemCount()
        else arrayList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (arrayList.isEmpty())
            super.getItemViewType(position)
        else {
            R.layout.item_search
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (arrayList.isEmpty())
            super.onCreateViewHolder(parent, viewType)
        else {
            UserViewHolder(
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (arrayList.isEmpty())
            super.onBindViewHolder(holder, position)
        else {
            onBindItemViewHolder((holder as UserViewHolder), position)
        }
    }

    fun onBindItemViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(arrayList.get(position))
    }

    inner class UserViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding) {
        fun bind(data: SearchAdapterData) {
            binding.user_img.setImageURI(data.url)
            binding.user_name.text = data.name
            binding.user_subname.text = data.subName

            binding.setOnClickListener(View.OnClickListener {
                onItemClickListener?.onClick(data)
            })
        }
    }
}