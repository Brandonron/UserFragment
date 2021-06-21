package com.example.userfragment.adapter.user

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userfragment.R
import kotlinx.android.synthetic.main.layout_user_item.view.*

class UserRecyclerAdapter(
    val context: Context,
    var arrayList: MutableList<UserAdapterData>,
    var onUserClickListener: OnUserClickListener
) : RecyclerView.Adapter<UserRecyclerAdapter.UserViewHolder>() {

    fun setUserList(list: ArrayList<UserAdapterData>) {
        arrayList = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(context)

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        var root =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_user_item, parent, false)
        return UserViewHolder(root)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
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