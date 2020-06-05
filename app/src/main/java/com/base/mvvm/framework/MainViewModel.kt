package com.base.mvvm.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.base.mvvm.libcommon.http.DataResult
import com.base.mvvm.libcommon.http.getDate
import com.base.mvvm.libcommon.http.getListDate

class MainViewModel(app:Application) :AndroidViewModel(app){




    fun load():LiveData<DataResult<List<String>>>{
        val result=viewModelScope.getListDate<String>("https://www.wanandroid.com/banner/json")
        return result
    }
}