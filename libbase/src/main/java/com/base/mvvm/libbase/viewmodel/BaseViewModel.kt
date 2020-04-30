package com.base.mvvm.libbase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseViewModel(application: Application) : AndroidViewModel(application) {


    suspend fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        withContext(Dispatchers.Main) { block() }
    }

    suspend fun launchOnIO(block: suspend CoroutineScope.() -> Unit) {
        withContext(Dispatchers.IO) { block() }
    }

}