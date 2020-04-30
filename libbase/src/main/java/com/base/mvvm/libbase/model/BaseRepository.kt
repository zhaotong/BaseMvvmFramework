package com.base.mvvm.libbase.model

abstract class BaseRepository {


    abstract fun loadData(
        url: String? = null,
        params: Map<String, Any>? = null,
        refresh: Boolean = true,// true 刷新  false  加载更多
        page: Int = 0
    )
}