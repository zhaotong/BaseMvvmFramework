package com.base.mvvm.libbase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.base.mvvm.libbase.model.BaseModel

abstract class BaseViewModel<M : BaseModel>(application: Application) :
    AndroidViewModel(application) {

    var model: BaseModel? = null

    public fun loadData(url: String, params: Map<String, Any>) {
        model?.loadData(url,params)
    }

}