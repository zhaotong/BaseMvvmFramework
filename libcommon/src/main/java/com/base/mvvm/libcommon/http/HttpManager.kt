package com.base.mvvm.libcommon.http

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import retrofit2.HttpException
import java.lang.reflect.Type

/**
 * Created by tone on 2020/5/11
 */
class HttpManager {


    companion object {

        fun initHttpConfig(okHttpConfig: (RequestApi.() -> Unit)? = null) {
            RequestApi.initHttpConfig(okHttpConfig)
        }
    }
}

sealed class DataResult<out T> {

    data class Success<out T>(val data: T) : DataResult<T>()
    data class Failure(val msg: String, val code: Int) : DataResult<Nothing>()

    internal class Start<T> : DataResult<T>()


    companion object {
        fun <T> start(): DataResult<T> {
            return Start()
        }
    }
}

data class HttpResult<out T>(

        var errorCode: Int = 0,
        var errorMsg: String = "",
        val data: T? = null
)


inline fun <reified T : Any> String.getListBean(): List<T>? {
    val moshi = Moshi.Builder().build()
    val type: Type = Types.newParameterizedType(List::class.java, T::class.java)
    val adapter: JsonAdapter<List<T>> = moshi.adapter(type)
    return adapter.fromJson(this)
}

inline fun <reified T : Any> String.getListBean2(): DataResult<List<T>>? {
    val moshi = Moshi.Builder().build()
    val type: Type = Types.newParameterizedType(DataResult::class.java, List::class.java, T::class.java)
    val adapter: JsonAdapter<DataResult<List<T>>> = moshi.adapter(type)
    return adapter.fromJson(this)
}

inline fun <reified T : Any> CoroutineScope.getListDate(url: String): LiveData<DataResult<List<T>>> =

        liveData {
            emit(DataResult.start<List<T>>())
            try {
//            val singleRunner = SingleRunner()
//            val controlledRunner =ControlledRunner

                val data = RequestApi.getDate(url)
                if (data.isSuccessful) {
                    val dataString = data.body()?.string()
                    Log.d("HttpManager",dataString)

                    val result = dataString?.getListBean2<T>()

                    emit(result as DataResult.Success<List<T>>)
                } else {
                    val exception = HttpException(data)
                    emit(DataResult.Failure(exception.message ?: "", exception.code()))
                }

            } catch (e: Exception) {
                e.printStackTrace()
                emit(DataResult.Failure(e.message ?: "", 0))
            }

        }


inline fun <reified T : Any> CoroutineScope.getDate(url: String): LiveData<DataResult<T>> =
        liveData(coroutineContext) {
        emit(DataResult.start<T>())
        try {
            val data = RequestApi.getDate(url)
            if (data.isSuccessful) {
                val dataString = data.body()?.string()
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val type: Type = Types.newParameterizedType(DataResult::class.java, T::class.java)
                val result = moshi.adapter(type::class.java).fromJson(dataString)
                emit(result as DataResult.Success<T>)
            } else {
                val exception = HttpException(data)
                emit(DataResult.Failure(exception.message ?: "", exception.code()))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataResult.Failure(e.message ?: "", 0))
        }

    }


inline fun <reified T : Any> CoroutineScope.postDate(url: String, map: Map<String, String>): LiveData<DataResult<T>> =

        liveData {
            emit(DataResult.start<T>())
            try {
                val data = RequestApi.postDate(url, map)
                if (data.isSuccessful) {
                    val dataString = data.body()?.string()
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val type: Type = Types.newParameterizedType(DataResult::class.java, T::class.java)
                    val result = moshi.adapter(type::class.java).fromJson(dataString)
                    emit(result as DataResult.Success<T>)
                } else {
                    val exception = HttpException(data)
                    emit(DataResult.Failure(exception.message ?: "", exception.code()))
                }

            } catch (e: Exception) {
                e.printStackTrace()
                emit(DataResult.Failure(e.message ?: "", 0))
            }

        }
