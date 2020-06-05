package com.base.mvvm.libcommon.http


import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ServiceApi {

    @FormUrlEncoded
    @POST
    fun postDate(@Url url: String, @FieldMap map: Map<String, String>): Call<ResponseBody>


    @GET
    fun getDate(@Url url: String): Call<ResponseBody>


    @POST
    fun uploadFile(@Url url: String, @Body body: MultipartBody): Call<ResponseBody>


    @POST
    fun downloadFile(@Url url: String, @FieldMap map: Map<String, String>): Call<ResponseBody>

}