package com.example.userfragment.adapter.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dramaproject.adapter.base.EmptyAdapter
import com.example.userfragment.R
import kotlinx.android.synthetic.main.item_user.view.*

class UserRecyclerAdapter(
    var onUserClickListener: OnRecyclerViewItemCallBack<UserAdapterData>
) : EmptyAdapter() {
    var arrayList: MutableList<UserAdapterData> = mutableListOf()

    fun updateList(list: ArrayList<UserAdapterData>?) {
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
            R.layout.item_user
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
        fun bind(data: UserAdapterData) {
            binding.user_img.setImageURI(data.url)
            binding.user_name.text = data.name
            binding.user_subname.text = data.subName

            binding.setOnClickListener(View.OnClickListener {
                onUserClickListener?.onClick(data)
            })
        }
    }
}