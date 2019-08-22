package com.hoppin.retrofitmodule

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


/**
 * Created by Ravi Birla on 14,August,2019
 */
interface Retrointerface {

    @Headers("Content-Type: application/json","Accept-Language: fr")
    @POST("public/sms/code/send")
    fun verificationCode(@Body obj: JsonObject): Call<JsonObject>

    @Headers("Content-Type: application/json","Accept-Language: fr")
    @POST("public/sms/code/verify")
    fun verifyCode(@Body obj: JsonObject): Call<JsonObject>


    @Headers("Content-Type: application/json","Accept-Language: fr")
    @POST("public/users/registration")
    fun signup(@Body obj: JsonObject): Call<JsonObject>

    @Headers("Content-Type: image/jpeg","Content-Length: 660")
    @PUT("/{Key}")
    fun uploadPhoto(@Path("Key") Key:String): Call<ResponseBody>

    @POST("oauth/token")
    @FormUrlEncoded
    fun signin(@Field("grant_type") grant_type: String,
               @Field("username") username: String,
               @Field("password") password: String): Call<ResponseBody>


    @Multipart
    @PUT
    fun uploadImageToAWS(@Url url:String,@Part filePart: MultipartBody.Part): Call<ResponseBody>

}