package com.base.mvvm.libcommon.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.base.mvvm.libcommon.model.ListItem

open class BaseHolder<T : ListItem>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {




    fun  updateUI(item: ListItem, position: Int){

    }


}