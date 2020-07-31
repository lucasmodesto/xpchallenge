package br.com.xpchallenge.data.remote.interceptor

import br.com.xpchallenge.data.BuildConfig
import br.com.xpchallenge.data.extensions.toMd5Hash
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {

    companion object {
        private const val API_KEY_QUERY_PARAM = "apikey"
        private const val HASH_QUERY_PARAM = "hash"
        private const val TIMESTAMP_QUERY_PARAM = "ts"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val timestamp = System.currentTimeMillis().toString()
        val hash = (timestamp + BuildConfig.API_PRIVATE_KEY + BuildConfig.API_PUBLIC_KEY).toMd5Hash()

        val originalRequest = chain.request()
        val url: HttpUrl = chain.request().url
            .newBuilder()
            .addQueryParameter(API_KEY_QUERY_PARAM, BuildConfig.API_PUBLIC_KEY)
            .addQueryParameter(HASH_QUERY_PARAM, hash)
            .addQueryParameter(TIMESTAMP_QUERY_PARAM, timestamp)
            .build()

        val newRequest = originalRequest.newBuilder().url(url).build()

        return chain.proceed(newRequest)
    }
}