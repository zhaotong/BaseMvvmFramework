package com.base.mvvm.libbase.model

abstract class BaseModel {

    abstract fun loadData(url: String, params: Map<String, Any>)

}