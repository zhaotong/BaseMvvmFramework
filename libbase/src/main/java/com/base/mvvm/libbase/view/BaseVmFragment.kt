package com.base.mvvm.libbase.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.base.mvvm.libbase.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType


open class BaseVmFragment<VM : BaseViewModel> : BaseFragment() {


    protected lateinit var mViewModel: BaseViewModel

    private fun initViewModel() {
        val vm = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
        mViewModel = ViewModelProvider(this).get(vm.javaClass)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }



}