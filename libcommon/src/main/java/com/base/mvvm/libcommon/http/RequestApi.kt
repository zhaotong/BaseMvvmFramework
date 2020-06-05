package com.base.mvvm.libcommon.http

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * Created by tone on 2020/5/9
 */
object RequestApi {

    private const val TIME_OUT = 8L

    private var BASE_URL = "http://www.baidu.com"


    private val okHttpClient: OkHttpClient by lazy { initHttpClient() }

    private val serviceApi: ServiceApi by lazy { getRetrofit() }

    private var okHttpConfig: (RequestApi.() -> Unit)? = null

    fun initHttpConfig(okHttpConfig: (RequestApi.() -> Unit)? = null) {
        RequestApi.okHttpConfig = okHttpConfig
    }

//    private var okHttpBuilder: ((OkHttpClient.Builder) -> Unit)? = null

    infix fun initOkHttpBuilder(builder: ((OkHttpClient.Builder) -> OkHttpClient.Builder?)?) {
//        this.okHttpBuilder = builder
       val okHttpBuilder= okHttpClient.newBuilder()
        builder?.invoke(okHttpBuilder)?.build()
    }

    private var baseUrl: (() -> String?)? = null
    infix fun initBaseUrl(base: (() -> String?)?) {
        baseUrl = base
    }


    private fun initHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        readTimeout(TIME_OUT, TimeUnit.SECONDS)
        writeTimeout(TIME_OUT, TimeUnit.SECONDS)
    }.build()


    private fun getRetrofit(): ServiceApi =
            Retrofit.Builder()
                    .apply {

                        baseUrl?.invoke()
                                ?.also {
                                    baseUrl(it)
                                } ?: baseUrl(BASE_URL)
                    }
                    .client(okHttpClient)
                    .build()
                    .create(ServiceApi::class.java)


    suspend fun postDate(url: String, map: Map<String, String>): Response<ResponseBody> = withContext(Dispatchers.IO) {
        serviceApi.postDate(url, map).execute()
    }

    suspend fun getDate(url: String): Response<ResponseBody> = withContext(Dispatchers.IO) {
        serviceApi.getDate(url).execute()
    }

}
