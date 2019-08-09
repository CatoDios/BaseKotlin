package com.cato.kotlinprueba.utils

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

import java.io.IOException

/**
 * Created by macintoshhd on 20/03/18.
 */

class AddHeadersInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val newBuilder = chain.request().newBuilder()
        newBuilder.addHeader("version", "1.0")
        return chain.proceed(newBuilder.build())
    }
}
