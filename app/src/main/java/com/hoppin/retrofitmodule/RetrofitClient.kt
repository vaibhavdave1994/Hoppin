package com.hoppin.retrofitmodule


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient



class RetrofitClient private constructor() {
    private val retrofit: Retrofit

    val api: Retrointerface
        get() = retrofit.create<Retrointerface>(Retrointerface::class.java)
    var clientBuilder = OkHttpClient.Builder()


    init {


        retrofit = Retrofit.Builder()
                .baseUrl(Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build()


    }

    companion object {
        private val Base_Url = "http://dev.hoopin.app:8080/v1/"
        private var minstance: RetrofitClient? = null

        val instance: RetrofitClient
            get() {
                if (minstance == null) {
                    minstance = RetrofitClient()
                }
                return minstance!!
            }
    }

}
