package com.cato.kotlinprueba.data.remote

import com.cato.kotlinprueba.BuildConfig
import com.cato.kotlinprueba.data.remote.ServiceFactory.builder
import com.cato.kotlinprueba.data.remote.ServiceFactory.httpClient
import com.cato.kotlinprueba.utils.AddHeadersInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceFactory {
    val API_BASE_URL = BuildConfig.BASE

    private val httpClient = OkHttpClient.Builder()
    private val builder = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    private val oAuthHttp = OkHttpClient.Builder()
    private val oAuthBuilder = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
        client.addNetworkInterceptor(AddHeadersInterceptor())
        client.addInterceptor(logging)

        val retrofit = builder.client(httpClient.build()).client(client.build()).build()
        return retrofit.create(serviceClass)
    }


    fun retrofit(): Retrofit {
        return builder
            .client(
                httpClient.build()
            )
            .build()
    }

    fun <S> createAuthService(serviceClass: Class<S>): S {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        val retrofit = oAuthBuilder.client(oAuthHttp.build()).client(client).build()
        return retrofit.create(serviceClass)
    }
}
