package com.base.mvvm.libcommon.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.base.mvvm.libcommon.model.ListItem

class BaseAdapter<T : ListItem> : RecyclerView.Adapter<BaseHolder<T>>() {

    var datas = mutableListOf<T>()
        set(value) {
            field=value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<T> {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int =datas.size

    override fun onBindViewHolder(holder: BaseHolder<T>, position: Int) {
        holder.updateUI(datas[position],position)
    }

}