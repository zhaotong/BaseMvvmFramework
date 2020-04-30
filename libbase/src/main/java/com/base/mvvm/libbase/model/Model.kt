package com.base.mvvm.libbase.model

open class BaseUiModel<T>(

    var loading: Boolean = false,//正在拉数据 刷新或者加载更多
    var error: String = "",//失败
    var result: T? = null,//成功
    var isLoadMore: Boolean = false, // 加载更多
    var isRefresh: Boolean = false // 刷新

)