package com.example.dramaproject.adapter.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userfragment.R

/***
 * 預設顯示資料異常或無資料時，顯示固定資訊
 */
abstract class EmptyAdapter(
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    override fun getItemCount(): Int {
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_empty
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return EmptyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindEmptyViewHolder(
            holder as EmptyViewHolder,
            position
        )
    }

    fun onBindEmptyViewHolder(
        holder: EmptyViewHolder,
        position: Int
    ) {
    }

    class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}